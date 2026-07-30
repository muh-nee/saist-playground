# Validate and sanitize content before embedding — length cap and moderation check
require 'openai'

MAX_DOC_LENGTH = 8_000 # ~2k tokens

class KnowledgeController < ApplicationController
  def add_document
    text   = params[:content].to_s.strip
    # SAFE — reject oversized documents
    return render json: { error: 'Document too long' }, status: 422 if text.length > MAX_DOC_LENGTH

    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

    # SAFE — run moderation before embedding
    mod = client.moderations(parameters: { input: text })
    if mod.dig('results', 0, 'flagged')
      return render json: { error: 'Content policy violation' }, status: 422
    end

    embedding = client.embeddings(parameters: { model: 'text-embedding-3-small', input: text })
    VectorStore.insert(content: text, embedding: embedding.dig('data', 0, 'embedding'))
    render json: { indexed: true }
  end
end
