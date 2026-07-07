// import "log/slog"

func handleLogin(username string) {
	slog.Info("login_attempt", "user", username)
	// authenticate...
}
