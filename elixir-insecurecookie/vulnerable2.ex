def remember(conn, token), do: put_resp_cookie(conn, "remember_me", token, http_only: false)
