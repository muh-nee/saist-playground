package main

import (
	"html"
	"html/template"
	"net/http"
	"net/url"
	"regexp"
	"strings"

	"github.com/gorilla/mux"
)

func sanitizeInput(input string) string {
	scriptRe := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := scriptRe.ReplaceAllString(input, "")
	
	dangerousRe := regexp.MustCompile(`(?i)<(iframe|object|embed|link|meta|style)[^>]*>.*?</\1>`)
	cleaned = dangerousRe.ReplaceAllString(cleaned, "")
	
	return html.EscapeString(cleaned)
}

func validateURL(rawURL string) bool {
	parsedURL, err := url.Parse(rawURL)
	if err != nil {
		return false
	}
	
	if parsedURL.Scheme != "http" && parsedURL.Scheme != "https" {
		return false
	}
	
	return true
}

func searchHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query().Get("q")
	category := r.URL.Query().Get("category")
	
	safeQuery := sanitizeInput(query)
	safeCategory := sanitizeInput(category)
	
	tmpl := template.Must(template.New("search").Parse(`<html><body>
	<h1>Search Results</h1>
	<p>Searching for: <strong>{{.Query}}</strong> in category: <em>{{.Category}}</em></p>
	<div id="results">
		<p>No results found for your search query.</p>
	</div>
	<form method="get">
		<input type="text" name="q" value="{{.Query}}" placeholder="Search...">
		<input type="text" name="category" value="{{.Category}}" placeholder="Category">
		<button type="submit">Search Again</button>
	</form>
	</body></html>`))
	
	data := struct {
		Query    string
		Category string
	}{
		Query:    safeQuery,
		Category: safeCategory,
	}
	
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.Header().Set("X-Content-Type-Options", "nosniff")
	w.Header().Set("X-Frame-Options", "DENY")
	w.Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	w.Write([]byte(buf.String()))
}

func profileHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	username := vars["username"]
	status := r.URL.Query().Get("status")
	
	safeUsername := sanitizeInput(username)
	safeStatus := sanitizeInput(status)
	
	tmpl := template.Must(template.New("profile").Parse(`<html><body>
	<h1>User Profile</h1>
	<div class="profile">
		<h2>{{.Username}}'s Profile</h2>
		<p>Status: {{.Status}}</p>
		<div id="profile-content">
			<!-- Profile content loaded safely -->
		</div>
	</div>
	<script>
	var currentUser = {{.Username | js}};
	console.log("Viewing profile of:", currentUser);
	document.title = "Profile - " + currentUser;
	</script>
	</body></html>`))
	
	data := struct {
		Username string
		Status   string
	}{
		Username: safeUsername,
		Status:   safeStatus,
	}
	
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.Header().Set("X-Content-Type-Options", "nosniff")
	w.Header().Set("X-Frame-Options", "DENY")
	w.Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	w.Write([]byte(buf.String()))
}

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/search", searchHandler).Methods("GET")
	r.HandleFunc("/profile/{username}", profileHandler).Methods("GET")
	
	http.ListenAndServe(":8080", r)
}