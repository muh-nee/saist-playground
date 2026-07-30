# Validate each entry's resolved path stays within the destination directory
require 'zip'

def extract_archive(zip_path, dest_dir)
  dest_real = File.realpath(dest_dir)
  Zip::File.open(zip_path) do |zip_file|
    zip_file.each do |entry|
      target = File.expand_path(entry.name, dest_real)
      # SAFE — reject any entry that would land outside dest_dir
      unless target.start_with?(dest_real + File::SEPARATOR)
        raise "Zip Slip detected: #{entry.name}"
      end
      FileUtils.mkdir_p(File.dirname(target))
      entry.extract(target)
    end
  end
end
