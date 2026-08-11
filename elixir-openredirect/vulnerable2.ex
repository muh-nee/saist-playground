def back(conn, _params), do: redirect(conn, external: get_req_header(conn, "referer") |> List.first())
