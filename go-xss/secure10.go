package main

import (
	"html"
	"html/template"
	"net/http"
	"regexp"
	"strings"
)

func sanitizeInput(input string) string {
	scriptRe := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := scriptRe.ReplaceAllString(input, "")
	
	dangerousRe := regexp.MustCompile(`(?i)<(iframe|object|embed|link|meta|style)[^>]*>.*?</\1>`)
	cleaned = dangerousRe.ReplaceAllString(cleaned, "")
	
	attrRe := regexp.MustCompile(`(?i)\s(on\w+|javascript:|data:)\s*=\s*["'][^"']*["']`)
	cleaned = attrRe.ReplaceAllString(cleaned, "")
	
	return html.EscapeString(cleaned)
}

func securityHeaders(next http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "text/html; charset=utf-8")
		w.Header().Set("X-Content-Type-Options", "nosniff")
		w.Header().Set("X-Frame-Options", "DENY")
		w.Header().Set("X-XSS-Protection", "1; mode=block")
		w.Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
		next(w, r)
	}
}

func homeHandler(w http.ResponseWriter, r *http.Request) {
	name := r.URL.Query().Get("name")
	welcome := r.URL.Query().Get("welcome")
	
	safeName := sanitizeInput(name)
	safeWelcome := sanitizeInput(welcome)
	
	tmpl := template.Must(template.New("home").Parse(`<html><body>
	<h1>Welcome to Our Site</h1>
	<div class="welcome-message">
		<p>{{.Welcome}} {{.Name}}!</p>
	</div>
	<p>This demonstrates XSS protection in native Go HTTP server.</p>
	</body></html>`))
	
	data := struct {
		Name    string
		Welcome string
	}{
		Name:    safeName,
		Welcome: safeWelcome,
	}
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	w.Write([]byte(buf.String()))
}

func searchHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query().Get("q")
	sortBy := r.URL.Query().Get("sort")
	filter := r.URL.Query().Get("filter")
	
	safeQuery := sanitizeInput(query)
	safeSortBy := sanitizeInput(sortBy)
	safeFilter := sanitizeInput(filter)
	
	validSorts := []string{"name", "date", "relevance"}
	sortValid := false
	for _, s := range validSorts {
		if safeSortBy == s {
			sortValid = true
			break
		}
	}
	if !sortValid {
		safeSortBy = "relevance"
	}
	
	tmpl := template.Must(template.New("search").Parse(`<html><body>
	<h1>Search Results</h1>
	<div class="search-info">
		<p>Query: <strong>{{.Query}}</strong></p>
		<p>Sort by: {{.Sort}}</p>  
		<p>Filter: {{.Filter}}</p>
	</div>
	<div id="results">
		<p>No results found.</p>
	</div>
	<script>
	// Safe: Using properly escaped JavaScript
	var searchParams = {
		query: {{.Query | js}},
		sort: {{.Sort | js}}, 
		filter: {{.Filter | js}}
	};
	console.log("Search performed:", searchParams);
	document.title = "Search: " + searchParams.query;
	</script>
	</body></html>`))
	
	data := struct {
		Query  string
		Sort   string
		Filter string
	}{
		Query:  safeQuery,
		Sort:   safeSortBy,
		Filter: safeFilter,
	}
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	w.Write([]byte(buf.String()))
}

func formHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "POST" {
		r.ParseForm()
		email := r.FormValue("email")
		comment := r.FormValue("comment")
		rating := r.FormValue("rating")
		
		safeEmail := sanitizeInput(email)
		safeComment := sanitizeInput(comment)
		safeRating := sanitizeInput(rating)
		
		emailRe := regexp.MustCompile(`^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`)
		if !emailRe.MatchString(safeEmail) {
			safeEmail = "Invalid email format"
		}
		
		validRatings := []string{"1", "2", "3", "4", "5"}
		ratingValid := false
		for _, r := range validRatings {
			if safeRating == r {
				ratingValid = true
				break
			}
		}
		if !ratingValid {
			safeRating = "1"
		}
		
		if len(safeComment) > 500 {
			safeComment = safeComment[:500] + "..."
		}
		
		tmpl := template.Must(template.New("form_result").Parse(`<html><body>
		<h1>Form Submitted Successfully</h1>
		<div class="form-data">
			<p><strong>Email:</strong> {{.Email}}</p>
			<p><strong>Rating:</strong> {{.Rating}} stars</p>
			<p><strong>Comment:</strong></p>
			<div class="comment-box" style="border: 1px solid #ccc; padding: 10px; background: #f9f9f9;">
				{{.Comment}}
			</div>
		</div>
		<script>
		var feedback = {
			email: {{.Email | js}},
			rating: {{.Rating | js}},
			comment: {{.Comment | js}}
		};
		console.log("Feedback received from:", feedback.email);
		document.title = "Thank you - " + feedback.email;
		</script>
		</body></html>`))
		
		data := struct {
			Email   string
			Rating  string
			Comment string
		}{
			Email:   safeEmail,
			Rating:  safeRating,
			Comment: safeComment,
		}
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		w.Write([]byte(buf.String()))
	} else {
		html := `<html><body>
		<h1>Feedback Form</h1>
		<form method="post">
			<p>Email: <input type="email" name="email" required maxlength="100"></p>
			<p>Rating: 
				<select name="rating" required>
					<option value="">Select rating</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
			</p>
			<p>Comment: <textarea name="comment" rows="4" cols="50" maxlength="500"></textarea></p>
			<p><input type="submit" value="Submit"></p>
		</form>
		</body></html>`
		
		w.Write([]byte(html))
	}
}

func main() {
	http.HandleFunc("/", securityHeaders(homeHandler))
	http.HandleFunc("/search", securityHeaders(searchHandler))
	http.HandleFunc("/form", securityHeaders(formHandler))
	
	http.ListenAndServe(":8080", nil)
}