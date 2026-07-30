# Explicit HTML escaping when building HTML strings programmatically
require 'erb'

class CommentsController < ApplicationController
  def show
    comment = ERB::Util.html_escape(params[:text]) # SAFE — explicit escape
    render html: "<div class='comment'>#{comment}</div>".html_safe
  end

  def preview
    content = h(params[:content]) # SAFE — h() is an alias for html_escape in Rails
    render html: "<div>#{content}</div>".html_safe
  end
end
