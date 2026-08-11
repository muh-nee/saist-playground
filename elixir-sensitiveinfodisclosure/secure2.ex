def show(conn, _params), do: json(conn, %{user_id: conn.assigns.current_user.id})
