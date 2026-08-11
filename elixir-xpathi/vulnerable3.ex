def find(document, path), do: :xmerl_xpath.string(String.to_charlist(path), document)
