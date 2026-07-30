# Array form of system() — no shell expansion, arguments passed directly
class ToolsController < ApplicationController
  def ping
    host = params[:host]
    # SAFE — array form prevents shell injection; each arg is passed verbatim
    output, _status = Open3.capture2("ping", "-c", "4", host)
    render plain: output
  end

  def convert_image
    file = params[:filename]
    # SAFE — no shell; arguments are not interpreted
    system("convert", file, "output.png")
    render json: { status: 'done' }
  end
end
