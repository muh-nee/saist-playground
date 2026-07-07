// import "log"
// import "strings"

func handleQuery(input string) {
	sanitized := strings.NewReplacer("\r", "", "\n", "").Replace(input)
	log.Printf("query: %s", sanitized)
	// process query...
}
