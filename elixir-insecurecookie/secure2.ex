def preference(conn, theme), do: put_resp_cookie(conn, "theme", theme, secure: true, http_only: true, same_site: "Strict")
