# Human review gate before any example is used for fine-tuning
require 'openai'

class FineTuneController < ApplicationController
  def create_finetune
    # SAFE — only use examples that have been reviewed and approved by a human
    approved_examples = TrainingExample.where(status: 'approved', reviewed_by: User.staff)

    return render json: { error: 'No approved examples' }, status: 422 if approved_examples.empty?

    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    jsonl  = approved_examples.map do |ex|
      JSON.generate({ messages: [{ role: 'user', content: ex.prompt }, { role: 'assistant', content: ex.response }] })
    end.join("\n")

    file = client.files.upload(parameters: { file: StringIO.new(jsonl), purpose: 'fine-tune' })
    client.fine_tuning.jobs.create(parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' })
    render json: { started: true }
  end
end
