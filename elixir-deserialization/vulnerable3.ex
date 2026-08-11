def restore(conn, params), do: params["state"] |> Base.decode64!() |> :erlang.binary_to_term()
