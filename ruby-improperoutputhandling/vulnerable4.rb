require 'openai'

client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

def process_task(client, task)
  response = client.chat(
    parameters: {
      model: 'gpt-4o-mini',
      messages: [{ role: 'user', content: task }]
    }
  )
  output = response.dig('choices', 0, 'message', 'content')
  puts output
end

process_task(client, ARGV[0])
