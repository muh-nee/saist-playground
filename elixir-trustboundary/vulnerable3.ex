def token(conn, params), do: Guardian.encode_and_sign(%{role: params["role"]})
