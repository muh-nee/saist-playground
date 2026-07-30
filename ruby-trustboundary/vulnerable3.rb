# Trusting user-supplied discount code to set price directly
class CheckoutController < ApplicationController
  def apply_discount
    # VULNERABLE — price determined by client, not server
    price = params[:price].to_f
    discount = params[:discount].to_f
    total = price - discount
    render json: { total: total }
  end
end
