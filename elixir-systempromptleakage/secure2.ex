def debug(conn, _params) do
  Logger.info("model request completed")
  json(conn, %{status: "ok"})
end
