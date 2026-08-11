def lookup(conn, params), do: :eldap.search(connection(), filter: {:equalityMatch, ~c"uid", escape_filter(params["uid"])})
