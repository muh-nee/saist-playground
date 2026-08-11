def find(conn, params), do: Repo.query("SELECT * FROM users WHERE id = $1", [params["id"]])
