package main

import (
	"encoding/xml"
	"fmt"
	"net/http"
	"strings"

	"github.com/ChrisTrenkamp/goxpath"
	"github.com/ChrisTrenkamp/goxpath/tree"
	"github.com/ChrisTrenkamp/goxpath/tree/xmltree"
)

type User struct {
	XMLName  xml.Name `xml:"user"`
	Username string   `xml:"username"`
	Password string   `xml:"password"`
	Role     string   `xml:"role"`
}

type Users struct {
	XMLName xml.Name `xml:"users"`
	Users   []User   `xml:"user"`
}

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<users>
    <user>
        <username>admin</username>
        <password>secret123</password>
        <role>administrator</role>
    </user>
    <user>
        <username>john</username>
        <password>password</password>
        <role>user</role>
    </user>
    <user>
        <username>guest</username>
        <password>guest</password>
        <role>guest</role>
    </user>
</users>`

func authenticateUser(w http.ResponseWriter, r *http.Request) {
	username := r.URL.Query().Get("username")
	password := r.URL.Query().Get("password")

	if username == "" || password == "" {
		http.Error(w, "Username and password required", http.StatusBadRequest)
		return
	}

	reader := strings.NewReader(xmlData)
	doc, err := xmltree.ParseXML(reader)
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	xpathQuery := fmt.Sprintf("//user[username='%s' and password='%s']", username, password)
	
	result, err := goxpath.ParseExpr(xpathQuery)
	if err != nil {
		http.Error(w, "XPath parsing error", http.StatusInternalServerError)
		return
	}

	res, err := result.Eval(tree.NodePos{Node: doc})
	if err != nil {
		http.Error(w, "XPath evaluation error", http.StatusInternalServerError)
		return
	}

	if nodeSet, ok := res.(tree.NodeSet); ok && len(nodeSet) > 0 {
		fmt.Fprintf(w, "Authentication successful for user: %s", username)
	} else {
		fmt.Fprintf(w, "Authentication failed")
	}
}

func main() {
	http.HandleFunc("/login", authenticateUser)
	fmt.Println("Server starting on :8080")
	fmt.Println("Example vulnerable request: /login?username=admin'%20or%20'1'='1&password=anything")
	http.ListenAndServe(":8080", nil)
}