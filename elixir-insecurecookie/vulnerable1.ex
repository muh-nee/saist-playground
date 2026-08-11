def login(conn, token), do: put_resp_cookie(conn, "session", token)
