def authenticate(conn, %{"user_id" => id}), do: assign(conn, :current_user, Accounts.get_authorized!(conn.assigns.account, id))
