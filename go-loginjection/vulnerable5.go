// import "log/slog"
// import "net/http"

func handleEvent(w http.ResponseWriter, r *http.Request) {
	event := r.FormValue("event")
	slog.Info("event: " + event)
	// record event...
}
