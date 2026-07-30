# Named bind parameters and scoped queries
class ReportsController < ApplicationController
  def sales_report
    region = params[:region]
    @data = Sale.where("region = :region", region: region) # SAFE — named placeholder
    render json: @data
  end

  def user_lookup
    term = params[:q]
    @results = User.where("name LIKE ?", "%#{term}%") # SAFE — value interpolated into placeholder
    render json: @results
  end

  def fetch_order(conn, order_id)
    stmt = conn.prepare("SELECT * FROM orders WHERE id = ?")
    stmt.execute(order_id.to_i) # SAFE — prepared statement
  end
end
