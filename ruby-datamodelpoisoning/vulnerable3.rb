# Training data URL fetched from user input — attacker controls training corpus
require 'openai'
require 'open-uri'

class ModelTrainer
  def train_from_url(dataset_url)
    # VULNERABLE — fetches arbitrary URL supplied by user as training data
    data = URI.open(dataset_url).read # VULNERABLE
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    file   = client.files.upload(parameters: { file: StringIO.new(data), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
  end
end
