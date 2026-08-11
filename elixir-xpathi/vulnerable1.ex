def find(document, id), do: :xmerl_xpath.string(~c"//user[@id='#{id}']", document)
