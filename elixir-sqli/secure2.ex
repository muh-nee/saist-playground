def search(conn, %{"name" => name}), do: Ecto.Adapters.SQL.query!(Repo, "SELECT * FROM users WHERE name = $1", [name])
