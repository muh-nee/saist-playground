# User-controlled size used directly — allocating huge arrays or strings
class DataController < ApplicationController
  def allocate
    size = params[:size].to_i
    buf = Array.new(size) # VULNERABLE — attacker can pass 2**31-1
    render json: { allocated: size }
  end

  def repeat_string
    count = params[:count].to_i
    text  = params[:text]
    render plain: text * count # VULNERABLE — unbounded repetition
  end
end
