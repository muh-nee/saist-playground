def fail(conn, error) do
  Logger.error(Exception.format(:error, error, __STACKTRACE__))
  send_resp(conn, 500, "internal server error")
end
