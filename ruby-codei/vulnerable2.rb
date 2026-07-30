# send() with user-controlled method name — arbitrary method dispatch
class ApiController < ApplicationController
  def dispatch
    method_name = params[:action_name]
    self.send(method_name) # VULNERABLE — any method on the controller
  end

  def call_helper
    klass = Object.const_get(params[:class_name]) # VULNERABLE — arbitrary constant lookup
    klass.send(params[:method])                    # VULNERABLE
  end
end
