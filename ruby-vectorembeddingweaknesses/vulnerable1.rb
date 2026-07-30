# User-controlled text embedded into vector store without sanitization — poisoning
require 'openai'

class KnowledgeController < ApplicationController
  def add_document
    text = params[:content] # VULNERABLE — attacker can embed adversarial instructions
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    embedding = client.embeddings(
      parameters: { model: 'text-embedding-3-small', input: text }
    )
    vector = embedding.dig('data', 0, 'embedding')
    # Store in vector DB — poisoned vector will be retrieved and fed to LLM
    VectorStore.insert(content: text, embedding: vector) # VULNERABLE
    render json: { indexed: true }
  end
end
