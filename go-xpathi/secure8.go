package main

import (
	"fmt"
	"net/http"
	"regexp"
	"strconv"
	"strings"

	"github.com/antchfx/xpath"
	"github.com/antchfx/xmlquery"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<audit_logs>
    <event timestamp="2023-01-15T10:30:00Z" severity="info">
        <type>user_login</type>
        <user>alice</user>
        <ip>192.168.1.100</ip>
        <status>success</status>
        <message>User logged in successfully</message>
    </event>
    <event timestamp="2023-01-15T10:35:00Z" severity="warning">
        <type>failed_login</type>
        <user>bob</user>
        <ip>192.168.1.101</ip>
        <status>failed</status>
        <message>Invalid password attempt</message>
    </event>
    <event timestamp="2023-01-15T10:40:00Z" severity="info">
        <type>data_access</type>
        <user>charlie</user>
        <ip>192.168.1.102</ip>
        <status>success</status>
        <message>Accessed public dashboard</message>
    </event>
    <event timestamp="2023-01-15T11:00:00Z" severity="error">
        <type>system_error</type>
        <user>system</user>
        <ip>127.0.0.1</ip>
        <status>error</status>
        <message>Database connection timeout</message>
    </event>
</audit_logs>`

type LogQueryValidator struct {
	ValidSeverities map[string]bool
	ValidEventTypes map[string]bool
}

func NewLogQueryValidator() *LogQueryValidator {
	return &LogQueryValidator{
		ValidSeverities: map[string]bool{
			"info":    true,
			"warning": true,
			"error":   true,
		},
		ValidEventTypes: map[string]bool{
			"user_login":   true,
			"failed_login": true,
			"data_access":  true,
			"system_error": true,
		},
	}
}

func (lqv *LogQueryValidator) IsValidSeverity(severity string) bool {
	return lqv.ValidSeverities[severity]
}

func (lqv *LogQueryValidator) IsValidEventType(eventType string) bool {
	return lqv.ValidEventTypes[eventType]
}

func validateSeverity(severity string) (string, error) {
	if len(severity) == 0 {
		return "", fmt.Errorf("severity cannot be empty")
	}
	
	if len(severity) > 10 {
		return "", fmt.Errorf("severity too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-z]+$`)
	if !validPattern.MatchString(severity) {
		return "", fmt.Errorf("severity must contain only lowercase letters")
	}
	
	return strings.TrimSpace(severity), nil
}

func validateUsername(username string) (string, error) {
	if len(username) == 0 {
		return "", fmt.Errorf("username cannot be empty")
	}
	
	if len(username) > 20 {
		return "", fmt.Errorf("username too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-zA-Z0-9_]+$`)
	if !validPattern.MatchString(username) {
		return "", fmt.Errorf("username must contain only alphanumeric characters and underscores")
	}
	
	return strings.TrimSpace(username), nil
}

func validateLimit(limitStr string) (int, error) {
	if len(limitStr) == 0 {
		return 10, nil
	}
	
	limit, err := strconv.Atoi(limitStr)
	if err != nil {
		return 0, fmt.Errorf("limit must be a number")
	}
	
	if limit < 1 || limit > 100 {
		return 0, fmt.Errorf("limit must be between 1 and 100")
	}
	
	return limit, nil
}

var preparedQueries = map[string]*xpath.Expr{
	"by_severity": nil,
	"by_user":     nil,
	"all_events":  nil,
}

func initPreparedQueries() error {
	var err error
	preparedQueries["by_severity"], err = xpath.Compile("//event[@severity=$severity]")
	if err != nil {
		return err
	}
	
	preparedQueries["by_user"], err = xpath.Compile("//event[user=$user]")
	if err != nil {
		return err
	}
	
	preparedQueries["all_events"], err = xpath.Compile("//event")
	if err != nil {
		return err
	}
	
	return nil
}

func searchAuditLogsSecure(w http.ResponseWriter, r *http.Request) {
	severity := r.URL.Query().Get("severity")
	user := r.URL.Query().Get("user")
	limitStr := r.URL.Query().Get("limit")
	
	limit, err := validateLimit(limitStr)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid limit: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewLogQueryValidator()
	
	var validSeverity, validUser string
	if severity != "" {
		validSeverity, err = validateSeverity(severity)
		if err != nil {
			http.Error(w, fmt.Sprintf("Invalid severity: %v", err), http.StatusBadRequest)
			return
		}
		
		if !validator.IsValidSeverity(validSeverity) {
			http.Error(w, "Severity level not recognized", http.StatusBadRequest)
			return
		}
	}
	
	if user != "" {
		validUser, err = validateUsername(user)
		if err != nil {
			http.Error(w, fmt.Sprintf("Invalid username: %v", err), http.StatusBadRequest)
			return
		}
	}

	doc, err := xmlquery.Parse(strings.NewReader(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	var events []*xmlquery.Node
	
	if validSeverity != "" {
		events = xmlquery.Find(doc, "//event")
		var filteredEvents []*xmlquery.Node
		for _, event := range events {
			if event.SelectAttr("severity") == validSeverity {
				filteredEvents = append(filteredEvents, event)
			}
		}
		events = filteredEvents
	} else if validUser != "" {
		events = xmlquery.Find(doc, "//event")
		var filteredEvents []*xmlquery.Node
		for _, event := range events {
			userNode := xmlquery.FindOne(event, "user")
			if userNode != nil && userNode.InnerText() == validUser {
				filteredEvents = append(filteredEvents, event)
			}
		}
		events = filteredEvents
	} else {
		events = xmlquery.Find(doc, "//event")
	}
	
	if len(events) == 0 {
		fmt.Fprintf(w, "No audit log entries found matching criteria")
		return
	}

	if len(events) > limit {
		events = events[:limit]
	}

	fmt.Fprintf(w, "Audit Log Entries (limited to %d):\n", limit)
	for _, event := range events {
		timestamp := event.SelectAttr("timestamp")
		severity := event.SelectAttr("severity")
		eventType := xmlquery.FindOne(event, "type")
		user := xmlquery.FindOne(event, "user")
		ip := xmlquery.FindOne(event, "ip")
		status := xmlquery.FindOne(event, "status")
		message := xmlquery.FindOne(event, "message")
		
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
		if message != nil {
			fmt.Fprintf(w, ", Message: %s", message.InnerText())
		}
		fmt.Fprintf(w, "\n")
	}
}

func listLogOptions(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Available query options:\n")
	fmt.Fprintf(w, "Severities: info, warning, error\n")
	fmt.Fprintf(w, "Users: alice, bob, charlie, system\n")
	fmt.Fprintf(w, "Limit: 1-100 (default: 10)\n")
	fmt.Fprintf(w, "\nExample queries:\n")
	fmt.Fprintf(w, "- /logs?severity=error\n")
	fmt.Fprintf(w, "- /logs?user=alice&limit=5\n")
	fmt.Fprintf(w, "- /logs?limit=20\n")
}

func main() {
	if err := initPreparedQueries(); err != nil {
		panic(fmt.Sprintf("Failed to initialize prepared queries: %v", err))
	}
	
	http.HandleFunc("/logs", searchAuditLogsSecure)
	http.HandleFunc("/logs/options", listLogOptions)
	fmt.Println("Server starting on :9087")
	fmt.Println("This version uses input validation and safe filtering instead of dynamic XPath")
	fmt.Println("Valid severities: info, warning, error")
	fmt.Println("Valid users: alice, bob, charlie, system")
	fmt.Println("Prepared queries prevent injection attacks")
	http.ListenAndServe(":9087", nil)
}