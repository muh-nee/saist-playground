require 'digest'
require 'open-uri'
require 'openai'

TRUSTED_DATASET_URL = 'https://internal.example.com/datasets/approved.jsonl'
EXPECTED_SHA256 = 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'

class ModelTrainer
  def train_from_trusted_url
    content = URI.open(TRUSTED_DATASET_URL).read
    if Digest::SHA256.hexdigest(content) != EXPECTED_SHA256
      raise 'integrity check failed'
    end
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    file = client.files.upload(parameters: { file: StringIO.new(content), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
  end
end
