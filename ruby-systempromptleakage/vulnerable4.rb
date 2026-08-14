require 'sinatra'
require 'openai'
require 'json'

client = OpenAI::Client.new(access_token: 'sk-xxx')

tools = [
  {
    type: 'function',
    function: {
      name: 'get_user_records',
      description: 'Retrieves all internal user records. Admin use only.',
      parameters: { type: 'object', properties: { user_id: { type: 'string' } } }
    }
  }
]

post '/chat' do
  client.chat(parameters: {
    model: 'gpt-4o',
    messages: [{ role: 'user', content: params[:message] }],
    tools: tools
  })
  json({ reply: 'ok' })
end

get '/debug/tools' do
  json({ tools: tools })
end
