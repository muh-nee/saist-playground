def debug(conn, _params), do: json(conn, %{debug_prompt: system_prompt})
