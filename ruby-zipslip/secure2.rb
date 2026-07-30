# Use basename only — discard any directory structure from the archive
require 'zip'

class ArchiveController < ApplicationController
  UPLOAD_DIR = "/var/app/extracted"

  def extract
    zip_path = params[:zip_file].path
    Zip::File.open(zip_path) do |zip|
      zip.each do |entry|
        # SAFE — only keep the final filename component, drop all directory parts
        safe_name = File.basename(entry.name)
        dest = File.join(UPLOAD_DIR, safe_name)
        entry.extract(dest)
      end
    end
    render json: { status: 'extracted' }
  end
end
