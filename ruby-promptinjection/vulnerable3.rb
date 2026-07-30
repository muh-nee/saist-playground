# Indirect prompt injection via user-controlled document fed into RAG pipeline
require 'openai'

class RagController < ApplicationController
  def query
    user_question = params[:question]
    # Retrieve a user-uploaded document by ID
    doc = Document.find(params[:doc_id])

    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          { role: 'system', content: 'Answer the question based on the provided document.' },
          # VULNERABLE — doc.content is user-controlled and may contain injection payloads
          { role: 'user', content: "Document: #{doc.content}\n\nQuestion: #{user_question}" }
        ]
      }
    )
    render json: { answer: resp.dig('choices', 0, 'message', 'content') }
  end
end
