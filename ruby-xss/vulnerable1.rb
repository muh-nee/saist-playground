# html_safe bypasses Rails auto-escaping — XSS via user-controlled input
class SearchController < ApplicationController
  def results
    query = params[:q]
    @message = "<b>Results for: #{query}</b>".html_safe # VULNERABLE
    render html: @message
  end

  def welcome
    name = params[:name]
    render html: "Welcome, #{name}!".html_safe # VULNERABLE
  end
end
