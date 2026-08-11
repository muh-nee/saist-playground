def run(conn, params), do: Code.eval_string(params["expression"])
