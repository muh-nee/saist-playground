# JSON.parse is safe — no object instantiation, only primitives
require 'json'

class DataController < ApplicationController
  def import
    raw = request.body.read
    data = JSON.parse(raw) # SAFE — JSON cannot instantiate arbitrary Ruby objects
    DataImporter.process(data)
    render json: { imported: true }
  end

  def load_preferences
    raw = cookies[:prefs]
    prefs = JSON.parse(raw) # SAFE
    render json: prefs
  end
end
