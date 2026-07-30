# Minimal-scope tools + explicit allowlist of callable functions
require 'openai'
require 'json'

ALLOWED_TOOLS = %w[get_weather get_product_info].freeze

def get_weather(city)
  WeatherService.fetch(city) # SAFE — read-only, scoped external call
end

def get_product_info(product_id)
  Product.find(product_id.to_i).slice(:name, :price, :description) # SAFE — read-only, by ID
end

def run_agent(user_query)
  client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
  resp   = client.chat(
    parameters: {
      model:    'gpt-4o',
      messages: [{ role: 'user', content: user_query }],
      tools: [
        { type: 'function', function: { name: 'get_weather',      description: 'Get weather for a city', parameters: { type: 'object', properties: { city: { type: 'string' } }, required: ['city'] } } },
        { type: 'function', function: { name: 'get_product_info', description: 'Get product details by ID', parameters: { type: 'object', properties: { product_id: { type: 'integer' } }, required: ['product_id'] } } }
      ]
    }
  )
  tool_call = resp.dig('choices', 0, 'message', 'tool_calls', 0)
  fn_name   = tool_call.dig('function', 'name')
  args      = JSON.parse(tool_call.dig('function', 'arguments'))

  raise ArgumentError, "Unknown tool: #{fn_name}" unless ALLOWED_TOOLS.include?(fn_name) # SAFE
  send(fn_name, *args.values)
end
