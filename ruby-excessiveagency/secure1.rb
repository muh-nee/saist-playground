# Scope file-read tool to a specific safe directory
require 'openai'
require 'json'

ALLOWED_DIR = File.expand_path('/var/app/reports')

def read_file(path)
  # SAFE — resolve and validate path stays within allowed directory
  full = File.expand_path(path, ALLOWED_DIR)
  raise ArgumentError, "Access denied" unless full.start_with?(ALLOWED_DIR + '/')
  File.read(full)
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
          description: 'Read a report file from /var/app/reports',
          parameters:  { type: 'object', properties: { path: { type: 'string' } }, required: ['path'] }
        }
      }]
    }
  )
  tool_call = resp.dig('choices', 0, 'message', 'tool_calls', 0)
  args = JSON.parse(tool_call.dig('function', 'arguments'))
  read_file(args['path']) # SAFE — validated inside the function
end
