require 'sinatra'
require 'openai'
require 'json'

client = OpenAI::Client.new(access_token: 'sk-xxx')

get '/context' do
  policy_docs = Retriever.new.get_relevant_documents(params[:q])
  policy_text = policy_docs.map(&:content).join("\n")
  client.chat(parameters: {
    model: 'gpt-4o',
    messages: [
      { role: 'system', content: policy_text },
      { role: 'user', content: params[:q] }
    ]
  })
  json({ policy: policy_text })
end
