# Document access without tenant/org scoping
class DocumentsController < ApplicationController
  def download
    doc = Document.find(params[:id]) # VULNERABLE — no org scope
    send_data doc.content, filename: doc.filename
  end

  def update
    doc = Document.find(params[:id]) # VULNERABLE
    doc.update!(title: params[:title], body: params[:body])
    render json: { updated: true }
  end
end
