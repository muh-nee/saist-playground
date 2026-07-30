# Validate and cap pagination parameters
class PaginationController < ApplicationController
  PAGE_SIZE = 100
  MAX_PAGE  = 1_000

  def index
    page  = [[params[:page].to_i, 0].max, MAX_PAGE].min # SAFE — clamp to [0, MAX_PAGE]
    offset = page * PAGE_SIZE
    @records = Record.offset(offset).limit(PAGE_SIZE)
    render json: @records
  end

  def power
    base = params[:base].to_i.clamp(-100, 100)   # SAFE
    exp  = params[:exp].to_i.clamp(0, 10)         # SAFE — cap exponent
    render json: { result: base**exp }
  end
end
