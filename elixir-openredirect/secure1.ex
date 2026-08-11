def next(conn, params) do
  path = Map.fetch!(params, "next")
  true = path in ["/dashboard", "/settings"]
  redirect(conn, to: path)
end
