def encode(conn, %{"offset" => offset}) do
  value = String.to_integer(offset)
  true = value in -32_768..32_767
  <<value::signed-integer-size(16)>>
end
