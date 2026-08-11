def login(conn, token), do: put_resp_cookie(conn, "session", token, secure: true, http_only: true, same_site: "Lax")
