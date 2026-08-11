def download(conn, %{"name" => name}), do: send_file(conn, 200, Path.join(upload_root(), name))
