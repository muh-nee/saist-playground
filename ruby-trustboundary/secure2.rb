# Server-side price calculation — client cannot manipulate totals
class CheckoutController < ApplicationController
  def apply_discount
    cart  = current_user.cart
    code  = params[:discount_code].to_s.upcase
    # SAFE — look up discount server-side; client supplies only the code string
    promo = PromoCode.active.find_by(code: code)
    total = cart.subtotal
    total -= promo.discount_amount if promo
    render json: { total: total.round(2) }
  end
end
