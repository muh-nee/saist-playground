# User input passed directly to shell commands — command injection
class ToolsController < ApplicationController
  def ping
    host = params[:host]
    output = `ping -c 4 #{host}` # VULNERABLE
    render plain: output
  end

  def convert_image
    file = params[:filename]
    system("convert #{file} output.png") # VULNERABLE
    render json: { status: 'done' }
  end
end
