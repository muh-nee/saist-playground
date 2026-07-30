# YAML.unsafe_load on user input — allows arbitrary object instantiation
require 'yaml'

class DataController < ApplicationController
  def import
    raw = request.body.read
    data = YAML.unsafe_load(raw) # VULNERABLE — can instantiate arbitrary Ruby objects
    DataImporter.process(data)
    render json: { imported: true }
  end

  def load_config
    config_str = params[:config]
    config = YAML.load(config_str) # VULNERABLE — YAML.load in older Ruby is unsafe
    render json: config
  end
end
