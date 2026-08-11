def summarize(conn, %{"document" => document}), do: ExOpenAI.chat_completion(messages: [system_message(), %{role: "user", content: document}])
