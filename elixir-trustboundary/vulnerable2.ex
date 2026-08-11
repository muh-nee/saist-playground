def authenticate(conn, %{"user_id" => id}), do: assign(conn, :current_user_id, id)
