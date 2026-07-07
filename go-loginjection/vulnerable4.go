// import "go.uber.org/zap"
// import "net/http"

func handleOrder(w http.ResponseWriter, r *http.Request) {
	logger, _ := zap.NewProduction()
	sugar := logger.Sugar()
	orderId := r.PathValue("orderId")
	sugar.Info("order placed: " + orderId)
	// fulfill order...
}
