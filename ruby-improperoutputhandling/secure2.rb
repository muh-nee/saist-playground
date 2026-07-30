# Validate LLM output against a schema before use; never execute raw model text
require 'openai'
require 'json'

class QueryBot
  ALLOWED_TABLES = %w[products orders users].freeze

  def suggest_filter(natural_language_query)
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          { role: 'system', content: 'Return ONLY a JSON object: {"table": "<name>", "column": "<col>", "value": "<val>"}' },
          { role: 'user',   content: natural_language_query }
        ]
      }
    )
    raw = resp.dig('choices', 0, 'message', 'content')
    parsed = JSON.parse(raw)

    # SAFE — validate table name against allowlist before querying
    raise ArgumentError, "Invalid table" unless ALLOWED_TABLES.include?(parsed['table'])
    # SAFE — use parameterized query, not LLM-generated SQL
    parsed['table'].constantize.where(parsed['column'] => parsed['value'])
  rescue JSON::ParserError
    raise ArgumentError, "Model returned invalid JSON"
  end
end
