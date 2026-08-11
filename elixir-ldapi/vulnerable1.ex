def lookup(conn, params), do: :eldap.search(connection(), filter: ~c"(uid=#{params["uid"]})")
