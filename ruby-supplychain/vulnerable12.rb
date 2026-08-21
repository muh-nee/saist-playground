require "ruby/openai"

class SetupController < ApplicationController
  def setup_deps
    client = OpenAI::Client.new(access_token: ENV["OPENAI_API_KEY"])
    feature = params[:feature]
    response = client.chat(
      parameters: {
        model: "gpt-4",
        messages: [{ role: "user", content: "List Ruby gems for: #{feature}. One gem name per line." }]
      }
    )
    gems = response.dig("choices", 0, "message", "content").strip.split("\n")
    gems.each do |gem_name|
      system("gem install #{gem_name.strip}")
    end
    render json: { installed: gems }
  end
end
