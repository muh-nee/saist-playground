# User-submitted examples passed directly to fine-tune API without validation
require 'openai'

class FineTuneController < ApplicationController
  def create_finetune
    examples = params[:examples] # VULNERABLE — user controls training data; can inject harmful examples
    client   = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

    # Write examples to JSONL and upload as training file without any filtering
    jsonl = examples.map { |ex| JSON.generate(ex) }.join("\n")
    file  = client.files.upload(parameters: { file: StringIO.new(jsonl), purpose: 'fine-tune' })

    client.fine_tuning.jobs.create(
      parameters: { training_file: file['id'], model: 'gpt-3.5-turbo' } # VULNERABLE
    )
    render json: { started: true }
  end
end
