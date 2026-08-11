def request(conn, params), do: Logger.warning("bad request", reason: sanitize_log_value(params["reason"]))
