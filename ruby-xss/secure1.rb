# Rails auto-escaping via ERB templates — never call html_safe on user input
class SearchController < ApplicationController
  def results
    @query = params[:q] # SAFE — pass to template; ERB escapes it with <%= %>
    render :results
  end

  def welcome
    @name = params[:name] # SAFE — template will escape this
    render :welcome
  end
end

# In the view (results.html.erb):
# <b>Results for: <%= @query %></b>   ← auto-escaped
