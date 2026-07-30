# Agent loop with hard iteration cap and input size limit
require 'openai'

MAX_STEPS  = 10    # SAFE — hard cap on agent iterations
MAX_TOKENS = 1024  # SAFE — hard cap on each completion
MAX_PROMPT = 4096  # SAFE — cap on user-supplied input length

class AgentRunner
  def run(task)
    raise ArgumentError, "Prompt too long" if task.length > MAX_PROMPT # SAFE

    client   = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    messages = [{ role: 'user', content: task }]
    steps    = 0

    loop do
      raise "Max agent steps reached" if steps >= MAX_STEPS # SAFE — prevents infinite loop
      resp   = client.chat(parameters: { model: 'gpt-4o', max_tokens: MAX_TOKENS, messages: messages })
      answer = resp.dig('choices', 0, 'message', 'content')
      break if answer.include?('DONE')
      messages << { role: 'assistant', content: answer }
      messages << { role: 'user',      content: 'Continue.' }
      steps += 1
    end
  end
end
