# Allowlist validation before running any command
require 'open3'

ALLOWED_TOOLS = %w[nmap curl wget].freeze

def run_tool(tool, target)
  raise ArgumentError, "Tool not allowed" unless ALLOWED_TOOLS.include?(tool) # SAFE — allowlist
  stdout, _err, _status = Open3.capture3(tool, target) # SAFE — array form
  stdout
end

def grep_log(logfile)
  # SAFE — restrict to known log directory, pass as separate arg
  safe_path = File.join("/var/log/app", File.basename(logfile))
  stdout, _err = Open3.capture2("grep", "ERROR", safe_path) # SAFE
  stdout
end
