def decode(conn), do: :erlang.binary_to_term(conn.body_params["payload"])
