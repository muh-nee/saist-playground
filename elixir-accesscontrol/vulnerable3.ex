def update(conn, params) do
  Account
  |> Repo.get!(params["id"])
  |> Account.changeset(params["account"])
  |> Repo.update()
end
