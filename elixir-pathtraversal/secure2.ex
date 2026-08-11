def download(conn, %{"name" => name}) do
  path = Path.expand(name, upload_root())
  true = String.starts_with?(path, Path.expand(upload_root()) <> "/")
  send_file(conn, 200, path)
end
