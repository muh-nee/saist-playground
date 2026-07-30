# Oj gem with object mode on user data — deserialization gadget chain
require 'oj'

class ImportController < ApplicationController
  def receive
    raw = request.body.read
    # VULNERABLE — :object mode allows instantiation of arbitrary Ruby classes
    data = Oj.load(raw, mode: :object)
    Record.create!(data)
    render json: { ok: true }
  end
end
