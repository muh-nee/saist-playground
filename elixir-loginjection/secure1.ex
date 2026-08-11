def login(conn, params), do: Logger.info("login", user: String.replace(params["user"], ~r/[\r\n]/, ""))
