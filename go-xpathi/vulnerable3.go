package main

import (
	"fmt"
	"net/http"
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
            <span class="phone" style="display:none;">555-0001</span>
            <span class="secret" style="display:none;">admin_secret_key_123</span>
        </div>
        <div class="user" data-role="user">
            <span class="username">john</span>
            <span class="email">john@company.com</span>
            <span class="phone" style="display:none;">555-0002</span>
            <span class="secret" style="display:none;">user_token_456</span>
        </div>
        <div class="user" data-role="guest">
            <span class="username">guest</span>
            <span class="email">guest@company.com</span>
            <span class="phone" style="display:none;">555-0003</span>
            <span class="secret" style="display:none;">guest_temp_789</span>
        </div>
    </div>
</body>
</html>`

func searchUsers(w http.ResponseWriter, r *http.Request) {
	role := r.URL.Query().Get("role")
	
	if role == "" {
		http.Error(w, "Role parameter required", http.StatusBadRequest)
		return
	}

	doc, err := html.Parse(strings.NewReader(htmlData))
	if err != nil {
		http.Error(w, "HTML parsing error", http.StatusInternalServerError)
		return
	}

	xpathQuery := fmt.Sprintf("//div[@class='user' and @data-role='%s']", role)
	
	users := htmlquery.Find(doc, xpathQuery)
	
	if len(users) == 0 {
		fmt.Fprintf(w, "No users found with role: %s", role)
		return
	}

	fmt.Fprintf(w, "Users with role '%s':\n", role)
	for _, user := range users {
		username := htmlquery.FindOne(user, ".//span[@class='username']")
		email := htmlquery.FindOne(user, ".//span[@class='email']")
		phone := htmlquery.FindOne(user, ".//span[@class='phone']")
		secret := htmlquery.FindOne(user, ".//span[@class='secret']")
		
		if username != nil && email != nil {
			fmt.Fprintf(w, "Username: %s, Email: %s", htmlquery.InnerText(username), htmlquery.InnerText(email))
			if phone != nil {
				fmt.Fprintf(w, ", Phone: %s", htmlquery.InnerText(phone))
			}
			if secret != nil {
				fmt.Fprintf(w, ", Secret: %s", htmlquery.InnerText(secret))
			}
			fmt.Fprintf(w, "\n")
		}
	}
}

func main() {
	http.HandleFunc("/users", searchUsers)
	fmt.Println("Server starting on :8082")
	fmt.Println("Example vulnerable request: /users?role=admin'%20or%20'1'='1")
	fmt.Println("This bypasses role filtering and exposes all user secrets")
	http.ListenAndServe(":8082", nil)
}