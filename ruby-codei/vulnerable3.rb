# instance_eval and class_eval with user input
class ScriptController < ApplicationController
  def run_script
    code = params[:code]
    Object.instance_eval(code) # VULNERABLE
  end

  def patch_class
    body = params[:body]
    String.class_eval(body) # VULNERABLE — modifies String class at runtime
  end
end
