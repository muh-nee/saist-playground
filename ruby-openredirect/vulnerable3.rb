# Sinatra open redirect
require 'sinatra'

get '/go' do
  target = params[:to]
  redirect target # VULNERABLE — no host validation
end

get '/login' do
  # after login...
  redirect params[:next] # VULNERABLE
end
