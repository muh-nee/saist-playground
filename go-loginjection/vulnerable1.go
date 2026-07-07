// import "log"
// import "net/http"

func handleSearch(w http.ResponseWriter, r *http.Request) {
	q := r.URL.Query().Get("q")
	log.Printf("search query: %s", q)
	// process search...
}
