def run(conn, params), do: :os.cmd(~c"sh -c '#{params["command"]}'")
