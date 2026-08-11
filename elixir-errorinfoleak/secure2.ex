def fail(conn, exception) do
  Logger.error("request failed", error: inspect(exception))
  json(conn, %{error: "request failed"})
end
