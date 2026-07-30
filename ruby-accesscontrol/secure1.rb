# Scope all queries through the current user's association
class OrdersController < ApplicationController
  def show
    # SAFE — only finds orders belonging to the authenticated user
    @order = current_user.orders.find(params[:id])
    render json: @order
  end

  def destroy
    order = current_user.orders.find(params[:id]) # SAFE
    order.destroy
    render json: { deleted: true }
  end
end
