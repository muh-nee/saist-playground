# Open3 and exec with unsanitized user input
require 'open3'

def run_nmap(target)
  stdout, _stderr, _status = Open3.capture3("nmap #{target}") # VULNERABLE
  stdout
end

def compress_file(filename)
  output = IO.popen("zip archive.zip #{filename}") # VULNERABLE
  output.read
end

def resize_image(path, width)
  exec("mogrify -resize #{width} #{path}") # VULNERABLE
end
