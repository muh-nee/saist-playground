def encode(conn, %{"offset" => offset}), do: <<String.to_integer(offset)::signed-integer-size(16)>>
