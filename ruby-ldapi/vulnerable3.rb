# LDAP injection via email search filter
require 'net/ldap'

def find_user_by_email(email)
  ldap = Net::LDAP.new(host: 'ldap.example.com', port: 636, encryption: :simple_tls)
  # VULNERABLE — email not validated or escaped before filter construction
  filter = Net::LDAP::Filter.construct("(mail=#{email})")
  ldap.search(base: "dc=example,dc=com", filter: filter, return_result: false) do |entry|
    return entry
  end
end
