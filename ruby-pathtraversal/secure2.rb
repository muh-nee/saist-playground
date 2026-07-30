# Allowlist of permitted filenames
class ReportsController < ApplicationController
  ALLOWED_REPORTS = %w[summary.pdf monthly.csv annual.xlsx].freeze
  BASE_DIR = "/var/app/reports"

  def fetch
    name = params[:name]
    raise ActionController::Forbidden unless ALLOWED_REPORTS.include?(name) # SAFE — allowlist

    render plain: File.read(File.join(BASE_DIR, name))
  end

  def delete_file
    name = params[:file]
    raise ActionController::Forbidden unless ALLOWED_REPORTS.include?(name) # SAFE

    File.delete(File.join("/tmp/uploads", name))
    render json: { deleted: true }
  end
end
