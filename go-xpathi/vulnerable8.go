package main

import (
	"fmt"
	"net/http"
	"strings"

	"github.com/antchfx/xpath"
	"github.com/antchfx/xmlquery"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<security_logs>
    <event timestamp="2023-01-15T10:30:00Z" severity="high">
        <type>login_attempt</type>
        <user>admin</user>
        <ip>192.168.1.100</ip>
        <status>success</status>
        <session_token>tok_adm_12345abcde</session_token>
    </event>
    <event timestamp="2023-01-15T10:35:00Z" severity="medium">
        <type>file_access</type>
        <user>john</user>
        <ip>192.168.1.101</ip>
        <status>success</status>
        <file_path>/secure/passwords.txt</file_path>
    </event>
    <event timestamp="2023-01-15T10:40:00Z" severity="low">
        <type>page_view</type>
        <user>guest</user>
        <ip>192.168.1.102</ip>
        <status>success</status>
        <page>/public/help.html</page>
    </event>
    <event timestamp="2023-01-15T11:00:00Z" severity="critical">
        <type>privilege_escalation</type>
        <user>hacker</user>
        <ip>10.0.0.50</ip>
        <status>blocked</status>
        <attack_vector>buffer_overflow</attack_vector>
    </event>
</security_logs>`

func searchSecurityLogs(w http.ResponseWriter, r *http.Request) {
	severity := r.URL.Query().Get("severity")
	user := r.URL.Query().Get("user")
	
	if severity == "" && user == "" {
		http.Error(w, "Either severity or user parameter required", http.StatusBadRequest)
		return
	}

	doc, err := xmlquery.Parse(strings.NewReader(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	var xpathExpr *xpath.Expr
	var xpathQuery string
	
	if severity != "" && user != "" {
		xpathQuery = fmt.Sprintf("//event[@severity='%s' and user='%s']", severity, user)
	} else if severity != "" {
		xpathQuery = fmt.Sprintf("//event[@severity='%s']", severity)
	} else {
		xpathQuery = fmt.Sprintf("//event[user='%s']", user)
	}

	xpathExpr, err = xpath.Compile(xpathQuery)
	if err != nil {
		http.Error(w, "XPath compilation error", http.StatusInternalServerError)
		return
	}

	events := xmlquery.QueryAll(doc, xpathExpr)
	
	if len(events) == 0 {
		fmt.Fprintf(w, "No security events found matching criteria")
		return
	}

	fmt.Fprintf(w, "Security Events:\n")
	for _, event := range events {
		timestamp := event.SelectAttr("timestamp")
		severity := event.SelectAttr("severity")
		eventType := xmlquery.FindOne(event, "type")
		user := xmlquery.FindOne(event, "user")
		ip := xmlquery.FindOne(event, "ip")
		status := xmlquery.FindOne(event, "status")
		
		fmt.Fprintf(w, "Time: %s, Severity: %s", timestamp, severity)
		if eventType != nil {
			fmt.Fprintf(w, ", Type: %s", eventType.InnerText())
		}
		if user != nil {
			fmt.Fprintf(w, ", User: %s", user.InnerText())
		}
		if ip != nil {
			fmt.Fprintf(w, ", IP: %s", ip.InnerText())
		}
		if status != nil {
			fmt.Fprintf(w, ", Status: %s", status.InnerText())
		}
		
		sessionToken := xmlquery.FindOne(event, "session_token")
		filePath := xmlquery.FindOne(event, "file_path")
		attackVector := xmlquery.FindOne(event, "attack_vector")
		
		if sessionToken != nil {
			fmt.Fprintf(w, ", Session: %s", sessionToken.InnerText())
		}
		if filePath != nil {
			fmt.Fprintf(w, ", File: %s", filePath.InnerText())
		}
		if attackVector != nil {
			fmt.Fprintf(w, ", Attack: %s", attackVector.InnerText())
		}
		
		fmt.Fprintf(w, "\n")
	}
}

func main() {
	http.HandleFunc("/logs", searchSecurityLogs)
	fmt.Println("Server starting on :8087")
	fmt.Println("Example vulnerable request: /logs?severity=high'%20or%20'1'='1")
	fmt.Println("This exposes all security logs including session tokens")
	http.ListenAndServe(":8087", nil)
}