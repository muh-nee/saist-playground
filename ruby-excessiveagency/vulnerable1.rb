# LLM agent can read arbitrary files — no path restriction
require 'openai'
require 'json'

def read_file(path)
  File.read(path) # VULNERABLE — LLM decides the path; can read /etc/passwd, keys, etc.
end

def run_agent(user_query)
  client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
  resp   = client.chat(
    parameters: {
      model:    'gpt-4o',
      messages: [{ role: 'user', content: user_query }],
      tools: [{
        type: 'function',
        function: {
          name:        'read_file',
          description: 'Read a file from disk',
          parameters:  { type: 'object', properties: { path: { type: 'string' } }, required: ['path'] }
        }
      }]
    }
  )
  tool_call = resp.dig('choices', 0, 'message', 'tool_calls', 0)
  args = JSON.parse(tool_call.dig('function', 'arguments'))
  read_file(args['path']) # VULNERABLE — no scoping of path
end
