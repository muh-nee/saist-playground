def stream(prompt), do: ExOpenAI.stream_chat_completion(prompt, max_tokens: 500) |> Enum.take(3)
