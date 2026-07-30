# Unsafe find_by_sql and string concatenation
class ReportsController < ApplicationController
  def sales_report
    region = params[:region]
    @data = Sale.find_by_sql("SELECT * FROM sales WHERE region = '#{region}'") # VULNERABLE
    render json: @data
  end

  def user_lookup
    term = params[:q]
    sql = "SELECT id, name, email FROM users WHERE name LIKE '%" + term + "%'" # VULNERABLE
    @results = ActiveRecord::Base.connection.execute(sql)
    render json: @results
  end
end
