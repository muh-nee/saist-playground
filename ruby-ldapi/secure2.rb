# Input validation + Net::LDAP::Filter.eq for safe LDAP queries
require 'net/ldap'

VALID_USERNAME = /\A[a-z0-9._-]{1,64}\z/i.freeze

class DirectoryController < ApplicationController
  def search
    user = params[:user]
    # SAFE — reject input that doesn't match allowlist pattern
    raise ArgumentError, "Invalid username" unless user.match?(VALID_USERNAME)

    ldap = Net::LDAP.new(host: ENV['LDAP_HOST'])
    filter = Net::LDAP::Filter.eq("cn", user) # SAFE — parameterized
    results = ldap.search(base: "dc=corp,dc=com", filter: filter)
    render json: results.map(&:dn)
  end
end
