# Clamp user-provided integers to safe bounds before use
class DataController < ApplicationController
  MAX_ALLOC = 10_000
  MAX_COUNT = 1_000

  def allocate
    size = params[:size].to_i.clamp(0, MAX_ALLOC) # SAFE — bounded
    buf = Array.new(size)
    render json: { allocated: size }
  end

  def repeat_string
    count = params[:count].to_i.clamp(0, MAX_COUNT) # SAFE
    text  = params[:text].to_s.slice(0, 500)
    render plain: text * count
  end
end
