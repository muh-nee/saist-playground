def next(conn, params) do
  uri = URI.parse(params["next"])
  true = uri.scheme == "https" and uri.host in ["example.com"]
  redirect(conn, external: URI.to_string(uri))
end
