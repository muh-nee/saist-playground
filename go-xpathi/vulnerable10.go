package main

import (
	"fmt"
	"net/http"
	"strings"

	"github.com/antchfx/xmlquery"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<access_control>
    <permissions>
        <role name="admin">
            <access>full</access>
            <resources>*</resources>
            <password>admin_super_secret_2023</password>
        </role>
        <role name="manager">
            <access>limited</access>
            <resources>reports,users</resources>
            <password>manager_pass_456</password>
        </role>
        <role name="employee">
            <access>basic</access>
            <resources>profile</resources>
            <password>emp_default_123</password>
        </role>
    </permissions>
    <users>
        <user id="1" role="admin">
            <username>superadmin</username>
            <email>admin@company.com</email>
            <api_key>sk_live_admin_xyz789</api_key>
            <two_factor_secret>GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ</two_factor_secret>
        </user>
        <user id="2" role="manager">
            <username>manager1</username>
            <email>manager@company.com</email>
            <api_key>sk_live_mgr_abc123</api_key>
            <two_factor_secret>MFRGG243FMNXW2ZjG</two_factor_secret>
        </user>
        <user id="3" role="employee">
            <username>john.doe</username>
            <email>john@company.com</email>
            <api_key>sk_live_emp_def456</api_key>
            <two_factor_secret>MFRGG243FMNXW2345</two_factor_secret>
        </user>
    </users>
</access_control>`

func authenticate(w http.ResponseWriter, r *http.Request) {
	username := r.URL.Query().Get("username")
	role := r.URL.Query().Get("role")

	if username == "" || role == "" {
		http.Error(w, "Username and role parameters required", http.StatusBadRequest)
		return
	}

	doc, err := xmlquery.Parse(strings.NewReader(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	userQuery := fmt.Sprintf("//user[username='%s' and @role='%s']", username, role)

	users := xmlquery.Find(doc, userQuery)

	if len(users) == 0 {
		http.Error(w, "Authentication failed", http.StatusUnauthorized)
		return
	}

	user := users[0]
	userRole := user.SelectAttr("role")
	email := xmlquery.FindOne(user, "email")
	apiKey := xmlquery.FindOne(user, "api_key")
	twoFactorSecret := xmlquery.FindOne(user, "two_factor_secret")

	roleQuery := fmt.Sprintf("//role[@name='%s']", userRole)
	roles := xmlquery.Find(doc, roleQuery)

	fmt.Fprintf(w, "Authentication successful!\n")

	if email != nil {
		fmt.Fprintf(w, "Email: %s\n", email.InnerText())
	}
	if apiKey != nil {
		fmt.Fprintf(w, "API Key: %s\n", apiKey.InnerText())
	}
	if twoFactorSecret != nil {
		fmt.Fprintf(w, "2FA Secret: %s\n", twoFactorSecret.InnerText())
	}

	if len(roles) > 0 {
		access := xmlquery.FindOne(roles[0], "access")
		resources := xmlquery.FindOne(roles[0], "resources")
		password := xmlquery.FindOne(roles[0], "password")

		if access != nil {
			fmt.Fprintf(w, "Access Level: %s\n", access.InnerText())
		}
		if resources != nil {
			fmt.Fprintf(w, "Resources: %s\n", resources.InnerText())
		}
		if password != nil {
			fmt.Fprintf(w, "Role Password: %s\n", password.InnerText())
		}
	}
}

func checkAccess(w http.ResponseWriter, r *http.Request) {
	resource := r.URL.Query().Get("resource")
	minRole := r.URL.Query().Get("minRole")

	if resource == "" || minRole == "" {
		http.Error(w, "Resource and minRole parameters required", http.StatusBadRequest)
		return
	}

	doc, err := xmlquery.Parse(strings.NewReader(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	accessQuery := fmt.Sprintf("//role[@name='%s' or resources='*' or contains(resources,'%s')]", minRole, resource)

	roles := xmlquery.Find(doc, accessQuery)

	if len(roles) == 0 {
		fmt.Fprintf(w, "No roles found with access\n")
		return
	}

	for _, role := range roles {
		roleName := role.SelectAttr("name")
		access := xmlquery.FindOne(role, "access")
		resources := xmlquery.FindOne(role, "resources")
		password := xmlquery.FindOne(role, "password")

		fmt.Fprintf(w, "Role: %s, Access: %s, Resources: %s",
			roleName, access.InnerText(), resources.InnerText())

		if password != nil {
			fmt.Fprintf(w, ", Password: %s", password.InnerText())
		}
		fmt.Fprintf(w, "\n")
	}
}

func main() {
	http.HandleFunc("/auth", authenticate)
	http.HandleFunc("/access", checkAccess)
	fmt.Println("Server starting on :8089")
	fmt.Println("Example vulnerable requests:")
	fmt.Println("  /auth?username=john.doe'%20or%20'1'='1&role=employee")
	fmt.Println("  /access?resource=admin'%20or%20'1'='1&minRole=employee")
	fmt.Println("These bypass authentication and expose all credentials")
	http.ListenAndServe(":8089", nil)
}
