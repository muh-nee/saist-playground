// import "github.com/rs/zerolog/log"
// import "net/http"

func handleProxy(w http.ResponseWriter, r *http.Request) {
	ip := r.Header.Get("X-Forwarded-For")
	log.Info().Msgf("request from: %s", ip)
	// proxy request...
}
