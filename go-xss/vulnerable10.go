package main

import (
	"fmt"
	"net/http"
)

func homeHandler(w http.ResponseWriter, r *http.Request) {
	name := r.URL.Query().Get("name")
	welcome := r.URL.Query().Get("welcome")
	
	html := fmt.Sprintf(`<html><body>
	<h1>Welcome to Our Site</h1>
	<div class="welcome-message">
		<p>%s %s!</p>
	</div>
	<p>This demonstrates XSS vulnerability in native Go HTTP server.</p>
	</body></html>`, welcome, name)
	
	w.Header().Set("Content-Type", "text/html")
	fmt.Fprint(w, html)
}

func searchHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query().Get("q")
	sortBy := r.URL.Query().Get("sort")
	filter := r.URL.Query().Get("filter")
	
	html := fmt.Sprintf(`<html><body>
	<h1>Search Results</h1>
	<div class="search-info">
		<p>Query: <strong>%s</strong></p>
		<p>Sort by: %s</p>  
		<p>Filter: %s</p>
	</div>
	<div id="results">
		<p>No results found.</p>
	</div>
	<script>
	var searchParams = {
		query: "%s",
		sort: "%s", 
		filter: "%s"
	};
	console.log("Search performed:", searchParams);
	document.title = "Search: " + searchParams.query;
	</script>
	</body></html>`, query, sortBy, filter, query, sortBy, filter)
	
	w.Header().Set("Content-Type", "text/html")
	fmt.Fprint(w, html)
}

func formHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "POST" {
		r.ParseForm()
		email := r.FormValue("email")
		comment := r.FormValue("comment")
		rating := r.FormValue("rating")
		
		html := fmt.Sprintf(`<html><body>
		<h1>Form Submitted Successfully</h1>
		<div class="form-data">
			<p><strong>Email:</strong> %s</p>
			<p><strong>Rating:</strong> %s stars</p>
			<p><strong>Comment:</strong></p>
			<div class="comment-box" style="border: 1px solid #ccc; padding: 10px; background: #f9f9f9;">
				%s
			</div>
		</div>
		<script>
		var feedback = {
			email: "%s",
			rating: %s,
			comment: "%s"
		};
		alert("Thank you " + feedback.email + " for your " + feedback.rating + " star rating!");
		</script>
		</body></html>`, email, rating, comment, email, rating, comment)
		
		w.Header().Set("Content-Type", "text/html")
		fmt.Fprint(w, html)
	} else {
		html := `<html><body>
		<h1>Feedback Form</h1>
		<form method="post">
			<p>Email: <input type="email" name="email" required></p>
			<p>Rating: <select name="rating"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></p>
			<p>Comment: <textarea name="comment" rows="4" cols="50"></textarea></p>
			<p><input type="submit" value="Submit"></p>
		</form>
		</body></html>`
		
		w.Header().Set("Content-Type", "text/html")
		fmt.Fprint(w, html)
	}
}

func main() {
	http.HandleFunc("/", homeHandler)
	http.HandleFunc("/search", searchHandler)
	http.HandleFunc("/form", formHandler)
	
	fmt.Println("Server starting on :8080")
	http.ListenAndServe(":8080", nil)
}