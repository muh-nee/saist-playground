def ping(conn, %{"host" => host}), do: System.cmd("sh", ["-c", "ping -c 1 #{host}"])
