# Allowlist / strict regex validation before using in XPath
require 'nokogiri'

SAFE_ID = /\A[a-z0-9_-]{1,50}\z/.freeze

def find_user(xml_doc, username)
  # SAFE — reject usernames that don't match the allowed character set
  raise ArgumentError, "Invalid username" unless username.match?(SAFE_ID)
  xml_doc.xpath("//user[name='#{username}']").first
end

def get_account(xml_doc, account_id)
  raise ArgumentError, "Invalid ID" unless account_id.match?(/\A\d+\z/) # SAFE — digits only
  xml_doc.xpath("//account[@id='#{account_id}']").first
end
