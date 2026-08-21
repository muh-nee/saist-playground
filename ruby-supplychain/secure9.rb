require "ruby/openai"

class DependencyController < ApplicationController
  APPROVED_GEMS = %w[onnxruntime torch-rb tensorflow ruby-openai anthropic].freeze

  def install
    client = OpenAI::Client.new(access_token: ENV["OPENAI_API_KEY"])
    task = params[:task]
    response = client.chat(
      parameters: {
        model: "gpt-4",
        messages: [{ role: "user", content: "What Ruby gem for: #{task}? Reply with only the gem name." }]
      }
    )
    gem_name = response.dig("choices", 0, "message", "content").strip
    unless APPROVED_GEMS.include?(gem_name)
      render json: { error: "gem not approved" }, status: :bad_request and return
    end
    system("gem install #{gem_name}")
    render json: { installed: gem_name }
  end
end
