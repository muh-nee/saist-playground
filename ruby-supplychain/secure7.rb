require "httparty"
require "digest"
require "tempfile"

WEIGHTS_URL = "https://cdn.example.com/models/weights.pt"
EXPECTED_SHA256 = "e3b0c44298fc1c149afbf4c8996fb924..."

def load_weights
  response = HTTParty.get(WEIGHTS_URL)
  raise "integrity check failed" if Digest::SHA256.hexdigest(response.body) != EXPECTED_SHA256
  tmp = Tempfile.new(["weights", ".pt"])
  tmp.write(response.body)
  tmp.rewind
  Torch.load(tmp.path)
end
