# Shell injection via string interpolation in system calls
class ProcessorController < ApplicationController
  def process_log
    logfile = params[:logfile]
    result = `grep ERROR #{logfile}` # VULNERABLE
    render plain: result
  end

  def generate_report
    date = params[:date]
    output = `python3 report.py --date #{date}` # VULNERABLE
    render plain: output
  end
end
