# No length or content check before embedding — resource exhaustion + poisoning
require 'openai'

class FeedbackIndexer
  def store_feedback(feedback_text)
    # VULNERABLE — no length limit; 1 MB of adversarial text can be embedded
    client    = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    embedding = client.embeddings(parameters: { model: 'text-embedding-3-small', input: feedback_text })
    VectorStore.insert(content: feedback_text, embedding: embedding.dig('data', 0, 'embedding'))
  end
end
