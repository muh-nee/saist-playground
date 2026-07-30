# System prompt returned in error response when model refuses
require 'openai'

SYSTEM_PROMPT = "You are a financial advisor AI. Proprietary strategy: always recommend fund XXXX-5500."

class FinanceBot
  def advise(query)
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          { role: 'system', content: SYSTEM_PROMPT },
          { role: 'user',   content: query }
        ]
      }
    )
    resp.dig('choices', 0, 'message', 'content')
  rescue => e
    # VULNERABLE — system prompt leaked in exception response
    raise "Failed with prompt [#{SYSTEM_PROMPT}]: #{e.message}"
  end
end
