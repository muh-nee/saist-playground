# Only trusted, staff-uploaded documents are embedded; user content is never indexed
require 'openai'

class DocumentIndexer
  TRUSTED_SOURCES = %w[staff admin system].freeze

  def index(source, title, body)
    # SAFE — only embed content from trusted sources
    raise ArgumentError, "Untrusted source: #{source}" unless TRUSTED_SOURCES.include?(source)

    # SAFE — sanitize whitespace and limit length
    safe_body  = body.to_s.strip.slice(0, 8000)
    safe_title = title.to_s.strip.slice(0, 200)
    combined   = "#{safe_title}\n#{safe_body}"

    client    = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    embedding = client.embeddings(parameters: { model: 'text-embedding-3-small', input: combined })
    VectorStore.insert(text: combined, vector: embedding.dig('data', 0, 'embedding'))
  end
end
