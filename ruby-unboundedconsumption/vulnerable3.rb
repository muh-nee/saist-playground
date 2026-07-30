# Recursive agent loop with no iteration limit — infinite LLM calls
require 'openai'

class AgentRunner
  def run(task)
    client   = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    messages = [{ role: 'user', content: task }]

    loop do # VULNERABLE — no step limit; attacker can craft tasks that loop forever
      resp   = client.chat(parameters: { model: 'gpt-4o', messages: messages })
      answer = resp.dig('choices', 0, 'message', 'content')
      break if answer.include?('DONE')
      messages << { role: 'assistant', content: answer }
      messages << { role: 'user',      content: 'Continue.' }
    end
  end
end
