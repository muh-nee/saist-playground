// import "github.com/rs/zerolog/log"

func handleLogin(username string) {
	log.Info().Str("user", username).Msg("login_attempt")
	// authenticate...
}
