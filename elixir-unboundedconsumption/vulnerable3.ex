def agent(prompt), do: Stream.repeatedly(fn -> LangChain.generate(chain(), prompt) end) |> Enum.to_list()
