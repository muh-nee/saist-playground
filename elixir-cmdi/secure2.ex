def ping(conn, %{"host" => host}) do
  true = Regex.match?(~r/\A[a-z0-9.-]+\z/, host)
  System.cmd("ping", ["-c", "1", host])
end
