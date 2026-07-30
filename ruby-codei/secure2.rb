# Safe math evaluation using a parser gem — never eval user input
require 'dentaku'

class CalculatorController < ApplicationController
  def compute
    expr = params[:expression]
    calculator = Dentaku::Calculator.new
    # SAFE — Dentaku parses math expressions without executing Ruby code
    result = calculator.evaluate(expr)
    render json: { result: result }
  rescue Dentaku::ParseError
    render json: { error: 'Invalid expression' }, status: :bad_request
  end
end
