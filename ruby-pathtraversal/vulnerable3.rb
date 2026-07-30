# Serving user-controlled path via Rack::File
class StaticController < ApplicationController
  PUBLIC_ROOT = "/var/app/public"

  def serve
    resource = params[:resource]
    file_path = File.join(PUBLIC_ROOT, resource) # VULNERABLE — traversal possible
    send_file file_path
  end

  def template_preview
    tpl = params[:template]
    content = File.read("/app/templates/#{tpl}") # VULNERABLE
    render plain: content
  end
end
