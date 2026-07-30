# User input interpolated into LDAP filter — LDAP injection
require 'net/ldap'

def authenticate_user(username, password)
  ldap = Net::LDAP.new(host: 'ldap.example.com', port: 389)
  # VULNERABLE — attacker can inject e.g. "*)(uid=*)(uid=" to bypass auth
  filter = Net::LDAP::Filter.construct("(uid=#{username})")
  ldap.search(base: "dc=example,dc=com", filter: filter)
end

def lookup_by_department(dept)
  ldap = Net::LDAP.new(host: 'ldap.example.com')
  filter = Net::LDAP::Filter.construct("(ou=#{dept})") # VULNERABLE
  ldap.search(base: "dc=example,dc=com", filter: filter)
end
