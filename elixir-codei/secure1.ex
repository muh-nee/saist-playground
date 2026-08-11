def run(conn, params), do: Map.fetch!(@allowed_expressions, params["name"]) |> Code.eval_string()
