def lookup(conn, %{"name" => name}), do: LDAPEx.search(client(), filter: "(cn=#{name})")
