def continue(conn, %{"url" => url}), do: Phoenix.Controller.redirect(conn, external: url)
