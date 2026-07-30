# Zip entry extracted without checking for path traversal — Zip Slip
require 'zip'

def extract_archive(zip_path, dest_dir)
  Zip::File.open(zip_path) do |zip_file|
    zip_file.each do |entry|
      # VULNERABLE — entry.name may be e.g. "../../etc/cron.d/evil"
      target = File.join(dest_dir, entry.name)
      entry.extract(target)
    end
  end
end

def unzip_upload(upload_path)
  Zip::File.open(upload_path) do |zip|
    zip.each do |f|
      out = "/var/app/uploads/#{f.name}" # VULNERABLE
      f.extract(out) { true }
    end
  end
end
