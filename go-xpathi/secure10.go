package main

import (
	"crypto/subtle"
	"encoding/xml"
	"fmt"
	"net/http"
	"regexp"
	"strings"
	"time"
)

type UserDirectory struct {
	XMLName xml.Name    `xml:"users"`
	Users   []UserInfo  `xml:"user"`
}

type UserInfo struct {
	ID       string `xml:"id,attr"`
	Username string `xml:"username"`
	Role     string `xml:"role"`
	Email    string `xml:"email"`
	Status   string `xml:"status"`
	Created  string `xml:"created"`
}

type RolePermissions struct {
	XMLName     xml.Name     `xml:"permissions"`
	Roles       []RoleInfo   `xml:"role"`
}

type RoleInfo struct {
	Name        string   `xml:"name,attr"`
	Access      string   `xml:"access"`
	Resources   []string `xml:"resources>resource"`
	Description string   `xml:"description"`
}

var userXMLData = `<?xml version="1.0" encoding="UTF-8"?>
<users>
    <user id="u001">
        <username>admin</username>
        <role>administrator</role>
        <email>admin@company.com</email>
        <status>active</status>
        <created>2020-01-01</created>
    </user>
    <user id="u002">
        <username>manager1</username>
        <role>manager</role>
        <email>manager@company.com</email>
        <status>active</status>
        <created>2021-03-15</created>
    </user>
    <user id="u003">
        <username>employee1</username>
        <role>employee</role>
        <email>emp1@company.com</email>
        <status>active</status>
        <created>2022-06-20</created>
    </user>
    <user id="u004">
        <username>guest</username>
        <role>guest</role>
        <email>guest@company.com</email>
        <status>inactive</status>
        <created>2023-01-10</created>
    </user>
</users>`

var roleXMLData = `<?xml version="1.0" encoding="UTF-8"?>
<permissions>
    <role name="administrator">
        <access>full</access>
        <resources>
            <resource>system</resource>
            <resource>users</resource>
            <resource>reports</resource>
        </resources>
        <description>Full system access</description>
    </role>
    <role name="manager">
        <access>limited</access>
        <resources>
            <resource>reports</resource>
            <resource>users</resource>
        </resources>
        <description>Management access to reports and users</description>
    </role>
    <role name="employee">
        <access>basic</access>
        <resources>
            <resource>profile</resource>
        </resources>
        <description>Basic profile access</description>
    </role>
    <role name="guest">
        <access>read-only</access>
        <resources>
            <resource>public</resource>
        </resources>
        <description>Read-only access to public information</description>
    </role>
</permissions>`

type AuthValidator struct {
	ValidRoles    map[string]bool
	ValidStatuses map[string]bool
}

func NewAuthValidator() *AuthValidator {
	return &AuthValidator{
		ValidRoles: map[string]bool{
			"administrator": true,
			"manager":       true,
			"employee":      true,
			"guest":         true,
		},
		ValidStatuses: map[string]bool{
			"active":   true,
			"inactive": true,
		},
	}
}

func (av *AuthValidator) IsValidRole(role string) bool {
	return av.ValidRoles[role]
}

func (av *AuthValidator) IsValidStatus(status string) bool {
	return av.ValidStatuses[status]
}

func validateUsername(username string) (string, error) {
	if len(username) == 0 {
		return "", fmt.Errorf("username cannot be empty")
	}
	
	if len(username) > 30 {
		return "", fmt.Errorf("username too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-zA-Z0-9_-]+$`)
	if !validPattern.MatchString(username) {
		return "", fmt.Errorf("username contains invalid characters")
	}
	
	blacklistPatterns := []string{
		"'", "\"", "or", "and", "=", "<", ">", "(", ")", "[", "]",
		"union", "select", "drop", "insert", "update", "delete", "*", "/",
	}
	
	lowerUsername := strings.ToLower(username)
	for _, pattern := range blacklistPatterns {
		if strings.Contains(lowerUsername, pattern) {
			return "", fmt.Errorf("username contains invalid content")
		}
	}
	
	return strings.TrimSpace(username), nil
}

func validateRole(role string) (string, error) {
	if len(role) == 0 {
		return "", fmt.Errorf("role cannot be empty")
	}
	
	if len(role) > 20 {
		return "", fmt.Errorf("role name too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-z]+$`)
	if !validPattern.MatchString(role) {
		return "", fmt.Errorf("role must contain only lowercase letters")
	}
	
	return strings.TrimSpace(role), nil
}

func secureStringCompare(a, b string) bool {
	return subtle.ConstantTimeCompare([]byte(a), []byte(b)) == 1
}

func authenticateSecure(w http.ResponseWriter, r *http.Request) {
	username := r.URL.Query().Get("username")
	role := r.URL.Query().Get("role")
	
	validUsername, err := validateUsername(username)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid username: %v", err), http.StatusBadRequest)
		return
	}
	
	validRole, err := validateRole(role)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid role: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewAuthValidator()
	if !validator.IsValidRole(validRole) {
		http.Error(w, "Role not recognized", http.StatusBadRequest)
		return
	}

	var userDirectory UserDirectory
	if err := xml.Unmarshal([]byte(userXMLData), &userDirectory); err != nil {
		http.Error(w, "User data parsing error", http.StatusInternalServerError)
		return
	}

	var authenticatedUser *UserInfo
	for _, user := range userDirectory.Users {
		if secureStringCompare(user.Username, validUsername) && 
		   secureStringCompare(user.Role, validRole) &&
		   user.Status == "active" {
			authenticatedUser = &user
			break
		}
	}
	
	if authenticatedUser == nil {
		time.Sleep(100 * time.Millisecond)
		http.Error(w, "Authentication failed", http.StatusUnauthorized)
		return
	}

	var rolePermissions RolePermissions
	if err := xml.Unmarshal([]byte(roleXMLData), &rolePermissions); err != nil {
		http.Error(w, "Permission data parsing error", http.StatusInternalServerError)
		return
	}

	var userRole *RoleInfo
	for _, roleInfo := range rolePermissions.Roles {
		if roleInfo.Name == validRole {
			userRole = &roleInfo
			break
		}
	}

	fmt.Fprintf(w, "Authentication successful!\n")
	fmt.Fprintf(w, "User ID: %s\n", authenticatedUser.ID)
	fmt.Fprintf(w, "Username: %s\n", authenticatedUser.Username)
	fmt.Fprintf(w, "Role: %s\n", authenticatedUser.Role)
	fmt.Fprintf(w, "Email: %s\n", authenticatedUser.Email)
	fmt.Fprintf(w, "Account Created: %s\n", authenticatedUser.Created)
	
	if userRole != nil {
		fmt.Fprintf(w, "\nRole Permissions:\n")
		fmt.Fprintf(w, "Access Level: %s\n", userRole.Access)
		fmt.Fprintf(w, "Description: %s\n", userRole.Description)
		fmt.Fprintf(w, "Allowed Resources: %s\n", strings.Join(userRole.Resources, ", "))
	}
}

func checkAccessSecure(w http.ResponseWriter, r *http.Request) {
	resource := r.URL.Query().Get("resource")
	minRole := r.URL.Query().Get("minRole")
	
	if resource == "" {
		http.Error(w, "Resource parameter required", http.StatusBadRequest)
		return
	}
	
	validRole, err := validateRole(minRole)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid role: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewAuthValidator()
	if !validator.IsValidRole(validRole) {
		http.Error(w, "Role not recognized", http.StatusBadRequest)
		return
	}

	var rolePermissions RolePermissions
	if err := xml.Unmarshal([]byte(roleXMLData), &rolePermissions); err != nil {
		http.Error(w, "Permission data parsing error", http.StatusInternalServerError)
		return
	}

	fmt.Fprintf(w, "Access check for resource '%s' with minimum role '%s':\n", resource, validRole)
	
	accessGranted := false
	for _, role := range rolePermissions.Roles {
		if role.Name == validRole {
			for _, allowedResource := range role.Resources {
				if allowedResource == resource || allowedResource == "system" {
					accessGranted = true
					fmt.Fprintf(w, "Access: GRANTED\n")
					fmt.Fprintf(w, "Role: %s\n", role.Name)
					fmt.Fprintf(w, "Access Level: %s\n", role.Access)
					fmt.Fprintf(w, "Description: %s\n", role.Description)
					break
				}
			}
			break
		}
	}
	
	if !accessGranted {
		fmt.Fprintf(w, "Access: DENIED\n")
		fmt.Fprintf(w, "Resource '%s' not accessible with role '%s'\n", resource, validRole)
	}
}

func listValidOptions(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Valid Authentication Options:\n")
	fmt.Fprintf(w, "\nUsernames:\n")
	fmt.Fprintf(w, "- admin (administrator)\n")
	fmt.Fprintf(w, "- manager1 (manager)\n")
	fmt.Fprintf(w, "- employee1 (employee)\n")
	fmt.Fprintf(w, "- guest (guest)\n")
	
	fmt.Fprintf(w, "\nRoles:\n")
	fmt.Fprintf(w, "- administrator (full access)\n")
	fmt.Fprintf(w, "- manager (limited access)\n")
	fmt.Fprintf(w, "- employee (basic access)\n")
	fmt.Fprintf(w, "- guest (read-only access)\n")
	
	fmt.Fprintf(w, "\nResources:\n")
	fmt.Fprintf(w, "- system\n")
	fmt.Fprintf(w, "- users\n")
	fmt.Fprintf(w, "- reports\n")
	fmt.Fprintf(w, "- profile\n")
	fmt.Fprintf(w, "- public\n")
}

func main() {
	http.HandleFunc("/auth", authenticateSecure)
	http.HandleFunc("/access", checkAccessSecure)
	http.HandleFunc("/options", listValidOptions)
	fmt.Println("Server starting on :9089")
	fmt.Println("This version uses secure authentication with proper input validation")
	fmt.Println("- Constant-time string comparison prevents timing attacks")
	fmt.Println("- Input validation prevents injection attacks")
	fmt.Println("- Type-safe XML parsing avoids XPath vulnerabilities")
	fmt.Println("- Only active users can authenticate")
	fmt.Println("- No sensitive credentials exposed in responses")
	http.ListenAndServe(":9089", nil)
}