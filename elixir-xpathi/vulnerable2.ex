def find(document, name), do: SweetXml.xpath(document, ~x"//user[name='#{name}']")
