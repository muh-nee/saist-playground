# Validate training example schema and run moderation before indexing
require 'openai'

class FeedbackTrainer
  MAX_EXAMPLES = 500

  def retrain_on_feedback
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

    # SAFE — take a capped number of examples and validate each one
    samples = UserFeedback.where(rating: 5).order('RANDOM()').limit(MAX_EXAMPLES)

    clean_examples = samples.filter_map do |fb|
      combined_text = "#{fb.prompt} #{fb.response}"
      # SAFE — reject examples that violate content policy
      mod = client.moderations(parameters: { input: combined_text })
      next if mod.dig('results', 0, 'flagged')

      { messages: [{ role: 'user', content: fb.prompt.slice(0, 2000) },
                   { role: 'assistant', content: fb.response.slice(0, 2000) }] }
    end

    jsonl  = clean_examples.map { |e| JSON.generate(e) }.join("\n")
    file   = client.files.upload(parameters: { file: StringIO.new(jsonl), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
  end
end
