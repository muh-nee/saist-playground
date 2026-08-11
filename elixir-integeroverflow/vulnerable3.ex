def send_size(conn, params), do: Port.command(port(), <<String.to_integer(params["size"])::unsigned-integer-size(64)>>)
