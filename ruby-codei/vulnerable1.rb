# eval() on user-supplied expression — remote code execution
class CalculatorController < ApplicationController
  def compute
    expr = params[:expression]
    result = eval(expr) # VULNERABLE — arbitrary Ruby evaluation
    render json: { result: result }
  end

  def render_template
    template = params[:template]
    ERB.new(template).result(binding) # VULNERABLE — user controls ERB with binding
  end
end
