# Raw SQL execution with user-controlled input
require 'active_record'

def fetch_order(conn, order_id)
  sql = "SELECT * FROM orders WHERE id = #{order_id}" # VULNERABLE
  conn.execute(sql)
end

def search_products(conn, category)
  sql = "SELECT * FROM products WHERE category = '#{category}' ORDER BY price" # VULNERABLE
  conn.execute(sql)
end

def get_user_by_email(conn, email)
  sql = "SELECT * FROM users WHERE email = '#{email}'" # VULNERABLE
  conn.execute(sql).first
end
