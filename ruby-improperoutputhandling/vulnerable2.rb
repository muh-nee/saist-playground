# LLM output passed directly to system() — command injection via model response
require 'openai'

class ShellAssistant
  def run_suggested_command(user_task)
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [{ role: 'user', content: "Give me a shell command to: #{user_task}" }]
      }
    )
    command = resp.dig('choices', 0, 'message', 'content')
    # VULNERABLE — executing LLM-generated string in the shell without any validation
    system(command)
  end
end
