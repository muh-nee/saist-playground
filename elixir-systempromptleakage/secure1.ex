def answer(conn, _params), do: json(conn, %{answer: ReqLLM.generate_text(model: model(), prompt: system_prompt).content})
