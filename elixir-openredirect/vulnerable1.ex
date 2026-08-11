def next(conn, params), do: redirect(conn, external: params["next"])
