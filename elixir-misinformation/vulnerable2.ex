def advise(conn, question), do: json(conn, %{legal_answer: ExOpenAI.chat_completion(question).content})
