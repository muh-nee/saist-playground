def read(conn, params), do: File.read!(Map.fetch!(@allowed_files, params["name"]))
