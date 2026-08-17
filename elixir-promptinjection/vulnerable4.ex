def summarize(conn, %{"query" => query}) do
  {:ok, response} = ExOpenAI.Chat.create_chat_completion(
    [%{role: "user", content: query}],
    "gpt-4o"
  )
  llm_output = response.choices |> List.first() |> get_in([:message, :content])
  LangChain.VectorStore.add_texts(vector_store(), [llm_output])
  json(conn, %{stored: true})
end
