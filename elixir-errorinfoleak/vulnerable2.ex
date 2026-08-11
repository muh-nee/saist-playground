def fail(conn, error), do: json(conn, %{error: Exception.format(:error, error, __STACKTRACE__)})
