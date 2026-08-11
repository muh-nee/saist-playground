def render(conn, params), do: Phoenix.HTML.raw(HtmlSanitizeEx.html5(params["html"]))
