require "net/http"

MODEL_URL = "https://cdn.example.com/models/model.marshal"

def load_model
  response = Net::HTTP.get(URI(MODEL_URL))
  Marshal.load(response)
end
