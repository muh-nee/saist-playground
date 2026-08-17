require 'openai'

class FeedbackController < ApplicationController
  def train_from_feedback
    training_data = params[:training_data]
    jsonl = training_data.map { |entry| JSON.generate(entry) }.join("\n")
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    file = client.files.upload(parameters: { file: StringIO.new(jsonl), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
    render json: { started: true }
  end
end
