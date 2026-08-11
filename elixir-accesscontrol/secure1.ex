def show(conn, params), do: Repo.get_by!(User, id: params["id"], account_id: conn.assigns.account.id)
