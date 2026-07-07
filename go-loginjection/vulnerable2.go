// import "log"
// import "net/http"

func handleLogin(w http.ResponseWriter, r *http.Request) {
	username := r.FormValue("username")
	log.Println("login for user: " + username)
	// authenticate...
}
