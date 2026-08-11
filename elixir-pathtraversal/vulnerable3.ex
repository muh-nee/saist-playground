def write(conn, params), do: File.write!(Path.join(storage_root(), params["name"]), params["body"])
