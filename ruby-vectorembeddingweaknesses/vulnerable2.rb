# Embedding user-supplied metadata that is later surfaced in LLM context
require 'openai'

class DocumentIndexer
  def index(user_id, title, body)
    combined = "Title: #{title}\nBody: #{body}\nAuthor ID: #{user_id}"
    # VULNERABLE — entire user-supplied content embedded verbatim, no validation
    # Adversarial content like "Ignore previous instructions..." in body will be stored
    client    = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    embedding = client.embeddings(parameters: { model: 'text-embedding-3-small', input: combined })
    VectorStore.insert(text: combined, vector: embedding.dig('data', 0, 'embedding'))
  end
end
