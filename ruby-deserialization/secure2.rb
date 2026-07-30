# YAML.safe_load limits deserialization to basic types
require 'yaml'

class DataController < ApplicationController
  ALLOWED_CLASSES = [Symbol, Time, Date].freeze

  def import
    raw = request.body.read
    # SAFE — safe_load only permits basic scalars and collections by default
    data = YAML.safe_load(raw, permitted_classes: ALLOWED_CLASSES)
    DataImporter.process(data)
    render json: { imported: true }
  end
end
