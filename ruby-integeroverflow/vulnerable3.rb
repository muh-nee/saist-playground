# User-controlled exponent in power operation — exponential resource use
class MathController < ApplicationController
  def power
    base = params[:base].to_i
    exp  = params[:exp].to_i
    result = base**exp # VULNERABLE — e.g. 999**999999 hangs the process
    render json: { result: result }
  end
end
