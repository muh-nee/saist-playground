package main

import (
	"fmt"
	"html"
	"net/http"
	"regexp"
	"strings"

	"golang.org/x/net/html"
	"github.com/antchfx/htmlquery"
)

var htmlData = `<!DOCTYPE html>
<html>
<head>
    <title>User Directory</title>
</head>
<body>
    <div class="users">
        <div class="user" data-role="admin">
            <span class="username">admin</span>
            <span class="email">admin@company.com</span>
            <span class="department">IT</span>
        </div>
        <div class="user" data-role="user">
            <span class="username">john</span>
            <span class="email">john@company.com</span>
            <span class="department">Sales</span>
        </div>
        <div class="user" data-role="guest">
            <span class="username">guest</span>
            <span class="email">guest@company.com</span>
            <span class="department">Support</span>
        </div>
    </div>
</body>
</html>`

type RoleValidator struct {
	AllowedRoles map[string]bool
}

func NewRoleValidator() *RoleValidator {
	return &RoleValidator{
		AllowedRoles: map[string]bool{
			"admin": true,
			"user":  true,
			"guest": true,
		},
	}
}

func (rv *RoleValidator) IsValidRole(role string) bool {
	return rv.AllowedRoles[role]
}

func sanitizeHTMLInput(input string) (string, error) {
	if len(input) == 0 {
		return "", fmt.Errorf("input cannot be empty")
	}
	
	if len(input) > 15 {
		return "", fmt.Errorf("input too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-z]+$`)
	if !validPattern.MatchString(input) {
		return "", fmt.Errorf("input must contain only lowercase letters")
	}
	
	escaped := html.EscapeString(input)
	
	return strings.TrimSpace(escaped), nil
}

func searchUsersSecure(w http.ResponseWriter, r *http.Request) {
	role := r.URL.Query().Get("role")
	
	sanitizedRole, err := sanitizeHTMLInput(role)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid role: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewRoleValidator()
	if !validator.IsValidRole(sanitizedRole) {
		http.Error(w, "Role not recognized", http.StatusBadRequest)
		return
	}

	doc, err := html.Parse(strings.NewReader(htmlData))
	if err != nil {
		http.Error(w, "HTML parsing error", http.StatusInternalServerError)
		return
	}

	allUsers := htmlquery.Find(doc, "//div[@class='user']")
	
	var matchingUsers []*html.Node
	for _, user := range allUsers {
		dataRole := htmlquery.SelectAttr(user, "data-role")
		if dataRole == sanitizedRole {
			matchingUsers = append(matchingUsers, user)
		}
	}
	
	if len(matchingUsers) == 0 {
		fmt.Fprintf(w, "No users found with role: %s", sanitizedRole)
		return
	}

	fmt.Fprintf(w, "Users with role '%s':\n", sanitizedRole)
	for _, user := range matchingUsers {
		username := htmlquery.FindOne(user, ".//span[@class='username']")
		email := htmlquery.FindOne(user, ".//span[@class='email']")
		department := htmlquery.FindOne(user, ".//span[@class='department']")
		
		if username != nil && email != nil && department != nil {
			fmt.Fprintf(w, "Username: %s, Email: %s, Department: %s\n", 
				htmlquery.InnerText(username), 
				htmlquery.InnerText(email),
				htmlquery.InnerText(department))
		}
	}
}

func getUserByUsername(w http.ResponseWriter, r *http.Request) {
	username := r.URL.Query().Get("username")
	
	sanitizedUsername, err := sanitizeHTMLInput(username)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid username: %v", err), http.StatusBadRequest)
		return
	}

	doc, err := html.Parse(strings.NewReader(htmlData))
	if err != nil {
		http.Error(w, "HTML parsing error", http.StatusInternalServerError)
		return
	}

	allUsers := htmlquery.Find(doc, "//div[@class='user']")
	
	for _, user := range allUsers {
		usernameNode := htmlquery.FindOne(user, ".//span[@class='username']")
		if usernameNode != nil && htmlquery.InnerText(usernameNode) == sanitizedUsername {
			email := htmlquery.FindOne(user, ".//span[@class='email']")
			department := htmlquery.FindOne(user, ".//span[@class='department']")
			role := htmlquery.SelectAttr(user, "data-role")
			
			fmt.Fprintf(w, "User found:\n")
			fmt.Fprintf(w, "Username: %s\n", sanitizedUsername)
			if email != nil {
				fmt.Fprintf(w, "Email: %s\n", htmlquery.InnerText(email))
			}
			if department != nil {
				fmt.Fprintf(w, "Department: %s\n", htmlquery.InnerText(department))
			}
			fmt.Fprintf(w, "Role: %s\n", role)
			return
		}
	}
	
	fmt.Fprintf(w, "User not found: %s", sanitizedUsername)
}

func main() {
	http.HandleFunc("/users", searchUsersSecure)
	http.HandleFunc("/user", getUserByUsername)
	fmt.Println("Server starting on :9082")
	fmt.Println("This version uses input sanitization and HTML escaping")
	fmt.Println("Valid roles: admin, user, guest")
	fmt.Println("Valid usernames: admin, john, guest")
	http.ListenAndServe(":9082", nil)
}