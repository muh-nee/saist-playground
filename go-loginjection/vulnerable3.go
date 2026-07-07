// import "github.com/sirupsen/logrus"
// import "net/http"

func handleRequest(w http.ResponseWriter, r *http.Request) {
	logger := logrus.New()
	ip := r.Header.Get("X-Forwarded-For")
	logger.Info("request from: " + ip)
	// handle request...
}
