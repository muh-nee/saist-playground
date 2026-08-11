def summarize(conn, %{"document" => document}), do: ExOpenAI.chat_completion(messages: [%{role: "user", content: document}], tools: [])
