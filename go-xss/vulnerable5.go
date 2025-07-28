package main

import (
	"fmt"
	"net/http"

	"github.com/gorilla/mux"
)

func searchHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query().Get("q")
	category := r.URL.Query().Get("category")
	
	html := fmt.Sprintf(`<html><body>
	<h1>Search Results</h1>
	<p>Searching for: <strong>%s</strong> in category: <em>%s</em></p>
	<div id="results">
		<p>No results found for your search query.</p>
	</div>
	<form method="get">
		<input type="text" name="q" value="%s" placeholder="Search...">
		<input type="text" name="category" value="%s" placeholder="Category">
		<button type="submit">Search Again</button>
	</form>
	</body></html>`, query, category, query, category)
	
	w.Header().Set("Content-Type", "text/html")
	w.Write([]byte(html))
}

func profileHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	username := vars["username"]
	status := r.URL.Query().Get("status")
	
	html := fmt.Sprintf(`<html><body>
	<h1>User Profile</h1>
	<div class="profile">
		<h2>%s's Profile</h2>
		<p>Status: %s</p>
		<script>
		var currentUser = "%s";
		console.log("Viewing profile of: " + currentUser);
		</script>
	</div>
	</body></html>`, username, status, username)
	
	w.Header().Set("Content-Type", "text/html")
	w.Write([]byte(html))
}

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/search", searchHandler).Methods("GET")
	r.HandleFunc("/profile/{username}", profileHandler).Methods("GET")
	
	http.ListenAndServe(":8080", r)
}