# Arithmetic on user input used as offset/index without bounds check
class PaginationController < ApplicationController
  PAGE_SIZE = 100

  def index
    page  = params[:page].to_i
    offset = page * PAGE_SIZE # VULNERABLE — page can be a huge negative or overflow value
    @records = Record.offset(offset).limit(PAGE_SIZE)
    render json: @records
  end

  def slice
    start = params[:start].to_i
    len   = params[:len].to_i
    data  = (0..1_000_000).to_a
    render json: data[start, len] # VULNERABLE — negative start or huge len
  end
end
