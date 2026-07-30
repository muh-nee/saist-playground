# raw() helper and content_tag misuse — reflected XSS
class CommentsController < ApplicationController
  def show
    comment = params[:text]
    render html: raw("<div class='comment'>#{comment}</div>") # VULNERABLE
  end

  def preview
    content = params[:content]
    # VULNERABLE — raw output without escaping
    render inline: "<%= raw @content %>", locals: { content: content }
  end
end
