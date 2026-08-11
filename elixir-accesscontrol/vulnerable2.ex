def delete(conn, %{"id" => id}), do: Repo.delete!(Repo.get!(Document, id))
