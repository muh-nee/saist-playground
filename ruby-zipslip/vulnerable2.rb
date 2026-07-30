# Tar extraction without path validation — similar slip pattern
require 'rubygems/package'

def extract_tarball(tar_path, dest)
  File.open(tar_path, 'rb') do |f|
    Gem::Package::TarReader.new(f) do |tar|
      tar.each do |entry|
        target = File.join(dest, entry.full_name) # VULNERABLE — full_name may traverse
        File.write(target, entry.read) if entry.file?
      end
    end
  end
end
