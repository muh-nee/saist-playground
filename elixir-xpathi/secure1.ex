def find(document, id), do: :xmerl_xpath.string(~c"//user", document) |> Enum.find(&(&1.id == id))
