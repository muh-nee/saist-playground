def run(conn, params), do: Map.fetch!(@operations, params["operation"]).()
