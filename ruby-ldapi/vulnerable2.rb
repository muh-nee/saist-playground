# String concatenation for LDAP filter construction
require 'net/ldap'

class DirectoryController < ApplicationController
  def search
    user = params[:user]
    ldap = Net::LDAP.new(host: ENV['LDAP_HOST'])
    # VULNERABLE — filter built by concatenation
    filter_str = "(&(objectClass=person)(cn=" + user + "))"
    filter = Net::LDAP::Filter.construct(filter_str)
    results = ldap.search(base: "dc=corp,dc=com", filter: filter)
    render json: results.map(&:dn)
  end

  def find_group(group_name)
    ldap = Net::LDAP.new(host: ENV['LDAP_HOST'])
    filter = Net::LDAP::Filter.construct("(cn=#{group_name})") # VULNERABLE
    ldap.search(base: "ou=groups,dc=corp,dc=com", filter: filter)
  end
end
