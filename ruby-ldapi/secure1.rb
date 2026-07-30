# Net::LDAP::Filter.eq() — safe parameterized LDAP filter
require 'net/ldap'

def authenticate_user(username, _password)
  ldap = Net::LDAP.new(host: 'ldap.example.com', port: 389)
  # SAFE — Filter.eq escapes special characters in the value
  filter = Net::LDAP::Filter.eq("uid", username)
  ldap.search(base: "dc=example,dc=com", filter: filter)
end

def lookup_by_department(dept)
  ldap = Net::LDAP.new(host: 'ldap.example.com')
  filter = Net::LDAP::Filter.eq("ou", dept) # SAFE
  ldap.search(base: "dc=example,dc=com", filter: filter)
end
