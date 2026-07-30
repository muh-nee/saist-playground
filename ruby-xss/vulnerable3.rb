# Sinatra-style rendering with interpolated user input
require 'sinatra'

get '/greet' do
  name = params[:name]
  "<h1>Hello, #{name}!</h1>" # VULNERABLE — returned as HTML body
end

get '/search' do
  q = params[:q]
  "<p>You searched for: #{q}</p>" # VULNERABLE
end
