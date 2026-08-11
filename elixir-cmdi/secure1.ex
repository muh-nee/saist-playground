def status(_conn, _params), do: System.cmd("git", ["status", "--short"])
