# File operations with unsanitized user path components
class ReportsController < ApplicationController
  BASE_DIR = "/var/app/reports"

  def fetch
    name = params[:name]
    full_path = File.join(BASE_DIR, name) # VULNERABLE — name may contain ../
    render plain: File.read(full_path)
  end

  def delete_file
    name = params[:file]
    File.delete("/tmp/uploads/#{name}") # VULNERABLE
    render json: { deleted: true }
  end
end
