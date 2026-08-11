def bind(conn, params) do
  dn = ~c"uid=#{params["user"]},ou=people"
  :eldap.simple_bind(connection(), dn, password())
end
