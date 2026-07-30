# Strip path components and validate the resolved path stays within base dir
class FilesController < ApplicationController
  BASE_DIR = "/var/app/reports"

  def download
    # SAFE — use only the basename, discard any directory components
    filename = File.basename(params[:filename])
    full_path = File.join(BASE_DIR, filename)
    render plain: File.read(full_path)
  end

  def fetch
    name = File.basename(params[:name]) # SAFE — basename strips traversal
    full_path = File.join(BASE_DIR, name)
    # SAFE — double-check resolved path is still inside BASE_DIR
    raise "Access denied" unless full_path.start_with?(BASE_DIR + "/")
    render plain: File.read(full_path)
  end
end
