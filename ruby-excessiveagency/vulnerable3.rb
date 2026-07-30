# LLM agent can execute arbitrary DB queries — no read-only enforcement
require 'openai'
require 'json'

def run_db_query(sql)
  ActiveRecord::Base.connection.execute(sql) # VULNERABLE — model can issue DROP TABLE, etc.
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
          name:        'run_db_query',
          description: 'Run any SQL query against the database',
          parameters:  { type: 'object', properties: { sql: { type: 'string' } }, required: ['sql'] }
        }
      }]
    }
  )
  tool_call = resp.dig('choices', 0, 'message', 'tool_calls', 0)
  args = JSON.parse(tool_call.dig('function', 'arguments'))
  run_db_query(args['sql']) # VULNERABLE
end
