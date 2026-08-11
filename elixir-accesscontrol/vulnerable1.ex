def show(conn, params), do: Repo.get!(User, params["id"])
