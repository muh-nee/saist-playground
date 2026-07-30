# User input interpolated into XPath query — XPath injection
require 'nokogiri'

def find_user(xml_doc, username)
  # VULNERABLE — attacker can inject e.g. "' or '1'='1" to bypass auth
  result = xml_doc.xpath("//user[name='#{username}']")
  result.first
end

def get_account(xml_doc, account_id)
  result = xml_doc.xpath("//account[@id='#{account_id}']") # VULNERABLE
  result.first
end
