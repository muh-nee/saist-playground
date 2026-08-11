def lookup(conn, %{"role" => role}) when role in ["admin", "viewer"], do: LDAPEx.search(client(), filter: "(role=#{role})")
