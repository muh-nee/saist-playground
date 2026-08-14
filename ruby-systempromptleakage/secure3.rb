require 'sinatra'
require 'openai'
require 'json'

client = OpenAI::Client.new(access_token: 'sk-xxx')

tools = [{ type: 'function', function: { name: 'get_data', description: 'Internal.', parameters: {} } }]

post '/chat' do
  policy_docs = Retriever.new.get_relevant_documents(params[:message])
  policy_text = policy_docs.map(&:content).join("\n")
  response = client.chat(parameters: {
    model: 'gpt-4o',
    messages: [
      { role: 'system', content: policy_text },
      { role: 'user', content: params[:message] }
    ],
    tools: tools
  })
  json({ reply: response.dig('choices', 0, 'message', 'content') })
end
