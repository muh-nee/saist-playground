def convert(conn, params), do: Porcelain.shell("convert #{params["file"]} output.png")
