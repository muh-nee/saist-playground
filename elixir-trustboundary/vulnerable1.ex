def login(conn, params), do: put_session(conn, :role, params["role"])
