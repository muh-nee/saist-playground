def compile(conn, params), do: Code.eval_quoted(Code.string_to_quoted!(params["code"]))
