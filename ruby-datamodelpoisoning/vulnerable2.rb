# Crowd-sourced feedback used directly as training signal — poisoning via thumbs-up
require 'openai'

class FeedbackTrainer
  def retrain_on_feedback
    # VULNERABLE — all positively rated interactions become training examples without review
    positive_samples = UserFeedback.where(rating: 5).pluck(:prompt, :response)
    examples = positive_samples.map do |(prompt, response)|
      { messages: [{ role: 'user', content: prompt }, { role: 'assistant', content: response }] }
    end

    jsonl  = examples.map { |e| JSON.generate(e) }.join("\n")
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    file   = client.files.upload(parameters: { file: StringIO.new(jsonl), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
  end
end
