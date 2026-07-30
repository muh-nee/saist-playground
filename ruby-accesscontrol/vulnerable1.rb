# Direct object reference without ownership check — IDOR
class OrdersController < ApplicationController
  def show
    @order = Order.find(params[:id]) # VULNERABLE — any user can access any order
    render json: @order
  end

  def destroy
    order = Order.find(params[:id]) # VULNERABLE — missing authorization check
    order.destroy
    render json: { deleted: true }
  end
end
