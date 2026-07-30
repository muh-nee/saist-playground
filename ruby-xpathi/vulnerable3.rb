# XPath search with unescaped user search term
require 'nokogiri'

class ProductsController < ApplicationController
  def search
    term = params[:q]
    doc  = Nokogiri::XML(File.read('catalog.xml'))
    items = doc.xpath("//product[contains(name,'#{term}')]") # VULNERABLE
    render json: items.map { |i| i['id'] }
  end
end
