require "ruby/openai"

class DependencyController < ApplicationController
  def install
    client = OpenAI::Client.new(access_token: ENV["OPENAI_API_KEY"])
    task = params[:task]
    response = client.chat(
      parameters: {
        model: "gpt-4",
        messages: [{ role: "user", content: "What Ruby gem should I use for: #{task}? Reply with only the gem name." }]
      }
    )
    gem_name = response.dig("choices", 0, "message", "content").strip
    system("gem install #{gem_name}")
    render json: { installed: gem_name }
  end
end
