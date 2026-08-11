def render(conn, params), do: html(conn, Phoenix.HTML.html_escape(params["message"]))
