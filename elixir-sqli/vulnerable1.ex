def find(conn, params), do: Repo.query("SELECT * FROM users WHERE id = #{params["id"]}")
