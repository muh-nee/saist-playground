def login(conn, params), do: put_session(conn, :role, Accounts.role_for(params["user_id"]))
