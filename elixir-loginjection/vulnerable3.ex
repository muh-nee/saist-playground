def audit(conn, %{"event" => event}), do: :logger.error("event=" <> event)
