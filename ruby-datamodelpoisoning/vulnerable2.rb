require 'openai'

class FeedbackController < ApplicationController
  def train_from_feedback
    prompt   = params[:prompt]
    response = params[:response]
    examples = [{ messages: [{ role: 'user', content: prompt }, { role: 'assistant', content: response }] }]
    jsonl    = examples.map { |e| JSON.generate(e) }.join("\n")
    client   = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    file     = client.files.upload(parameters: { file: StringIO.new(jsonl), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
    render json: { started: true }
  end
end
