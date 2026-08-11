def encode(conn, params), do: <<String.to_integer(params["size"])::unsigned-integer-size(32)>>
