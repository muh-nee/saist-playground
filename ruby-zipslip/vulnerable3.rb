# Zip extraction with overwrite allowed and no path check
require 'zip'

class ArchiveController < ApplicationController
  UPLOAD_DIR = "/var/app/extracted"

  def extract
    zip_path = params[:zip_file].path
    Zip::File.open(zip_path) do |zip|
      zip.each do |entry|
        dest = File.join(UPLOAD_DIR, entry.name) # VULNERABLE — no traversal check
        FileUtils.mkdir_p(File.dirname(dest))
        entry.extract(dest) { true } # true = overwrite
      end
    end
    render json: { status: 'extracted' }
  end
end
