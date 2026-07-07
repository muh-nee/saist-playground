// import "github.com/sirupsen/logrus"
// import "fmt"

func processUser(username string) error {
	logger := logrus.New()
	err := doWork(username)
	if err != nil {
		logger.Error(fmt.Sprintf("failed for user: %s", username))
		return err
	}
	return nil
}
