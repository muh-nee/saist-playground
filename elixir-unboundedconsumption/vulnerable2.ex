def stream(prompt), do: ExOpenAI.stream_chat_completion(prompt) |> Enum.to_list()
