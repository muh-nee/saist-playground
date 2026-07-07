// import "go.uber.org/zap"

func handleLogin(username string) {
	zap.L().Info("login_attempt", zap.String("user", username))
	// authenticate...
}
