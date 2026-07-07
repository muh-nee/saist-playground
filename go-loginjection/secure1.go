// import "github.com/sirupsen/logrus"

func handleLogin(username string) {
	logger := logrus.New()
	logger.WithField("user", username).Info("login_attempt")
	// authenticate...
}
