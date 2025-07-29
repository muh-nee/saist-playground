package main

import (
	"encoding/xml"
	"fmt"
	"net/http"
	"regexp"
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

func validateInput(input string) error {
	if len(input) == 0 {
		return fmt.Errorf("input cannot be empty")
	}
	
	if len(input) > 50 {
		return fmt.Errorf("input too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-zA-Z0-9_-]+$`)
	if !validPattern.MatchString(input) {
		return fmt.Errorf("input contains invalid characters")
	}
	
	maliciousPatterns := []string{
		"'", "\"", "or", "and", "=", "<", ">", "(", ")", "[", "]", 
		"union", "select", "drop", "delete", "insert", "update",
	}
	
	lowerInput := strings.ToLower(input)
	for _, pattern := range maliciousPatterns {
		if strings.Contains(lowerInput, pattern) {
			return fmt.Errorf("input contains potentially malicious content")
		}
	}
	
	return nil
}

func authenticateUserSecure(w http.ResponseWriter, r *http.Request) {
	username := r.URL.Query().Get("username")
	password := r.URL.Query().Get("password")

	if err := validateInput(username); err != nil {
		http.Error(w, fmt.Sprintf("Invalid username: %v", err), http.StatusBadRequest)
		return
	}
	
	if err := validateInput(password); err != nil {
		http.Error(w, fmt.Sprintf("Invalid password: %v", err), http.StatusBadRequest)
		return
	}

	reader := strings.NewReader(xmlData)
	doc, err := xmltree.ParseXML(reader)
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	userFound := false
	
	result, err := goxpath.ParseExpr("//user")
	if err != nil {
		http.Error(w, "XPath parsing error", http.StatusInternalServerError)
		return
	}

	res, err := result.Eval(tree.NodePos{Node: doc})
	if err != nil {
		http.Error(w, "XPath evaluation error", http.StatusInternalServerError)
		return
	}

	if nodeSet, ok := res.(tree.NodeSet); ok {
		for _, node := range nodeSet {
			usernameExpr, _ := goxpath.ParseExpr("username")
			passwordExpr, _ := goxpath.ParseExpr("password")
			
			usernameRes, _ := usernameExpr.Eval(tree.NodePos{Node: node})
			passwordRes, _ := passwordExpr.Eval(tree.NodePos{Node: node})
			
			if usernameStr, ok := usernameRes.(string); ok {
				if passwordStr, ok := passwordRes.(string); ok {
					if usernameStr == username && passwordStr == password {
						userFound = true
						break
					}
				}
			}
		}
	}

	if userFound {
		fmt.Fprintf(w, "Authentication successful for user: %s", username)
	} else {
		http.Error(w, "Authentication failed", http.StatusUnauthorized)
	}
}

func main() {
	http.HandleFunc("/login", authenticateUserSecure)
	fmt.Println("Server starting on :9080")
	fmt.Println("This version uses input validation to prevent XPath injection")
	fmt.Println("Valid usernames: admin, john, guest (alphanumeric only)")
	http.ListenAndServe(":9080", nil)
}