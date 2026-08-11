def find(document, field) when field in ["name", "email"], do: SweetXml.xpath(document, ~x"//user") |> select_field(field)
