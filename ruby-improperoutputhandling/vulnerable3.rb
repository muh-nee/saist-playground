# LLM-generated SQL executed directly
require 'openai'

class QueryBot
  def query_database(natural_language_query)
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [{ role: 'system', content: 'Translate the user query to SQL.' },
                   { role: 'user',   content: natural_language_query }]
      }
    )
    sql = resp.dig('choices', 0, 'message', 'content')
    # VULNERABLE — executing model-generated SQL directly against production DB
    ActiveRecord::Base.connection.execute(sql)
  end
end
