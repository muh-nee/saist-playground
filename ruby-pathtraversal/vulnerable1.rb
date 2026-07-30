# Direct use of user-supplied filename — path traversal
class FilesController < ApplicationController
  def download
    filename = params[:filename]
    content = File.read(filename) # VULNERABLE — e.g. ../../../../etc/passwd
    render plain: content
  end

  def show_log
    path = params[:path]
    send_file(path) # VULNERABLE
  end
end
