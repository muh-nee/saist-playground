require 'openai'

class AgentController < ApplicationController
  def chat
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

    tool_result = McpClient.call_tool('search', { query: params[:query] })

    unless tool_result.is_a?(Hash) && tool_result['result_count'].is_a?(Integer)
      raise 'Unexpected MCP tool output format'
    end

    result_summary = "Found #{tool_result['result_count']} results"

    response = client.chat(
      parameters: {
        model: 'gpt-4o',
        messages: [
          { role: 'system', content: 'You are a helpful search assistant.' },
          { role: 'user', content: result_summary }
        ]
      }
    )

    render json: { answer: response.dig('choices', 0, 'message', 'content') }
  end
end
