def show(conn, _params), do: json(conn, %{token: conn.assigns.current_user.token})
