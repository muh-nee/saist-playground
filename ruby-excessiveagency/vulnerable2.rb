# LLM agent can delete files — LLM-chosen path is passed without validation
require 'openai'
require 'json'

def delete_file(path)
  File.delete(path) # VULNERABLE — model can instruct deletion of any file
end

def send_email(to, subject, body)
  # Imagine this sends a real email
  Mailer.send(to: to, subject: subject, body: body)
end

def run_agent(user_instruction)
  client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
  resp   = client.chat(
    parameters: {
      model:    'gpt-4o',
      messages: [{ role: 'user', content: user_instruction }],
      tools: [
        {
          type: 'function',
          function: {
            name: 'delete_file',
            description: 'Delete a file',
            parameters: { type: 'object', properties: { path: { type: 'string' } }, required: ['path'] }
          }
        },
        {
          type: 'function',
          function: {
            name: 'send_email',
            description: 'Send an email to anyone',
            parameters: {
              type: 'object',
              properties: {
                to:      { type: 'string' },
                subject: { type: 'string' },
                body:    { type: 'string' }
              },
              required: ['to', 'subject', 'body']
            }
          }
        }
      ]
    }
  )
  tool_call = resp.dig('choices', 0, 'message', 'tool_calls', 0)
  args      = JSON.parse(tool_call.dig('function', 'arguments'))
  fn_name   = tool_call.dig('function', 'name')
  # VULNERABLE — blindly dispatching to high-impact functions
  send(fn_name, *args.values)
end
