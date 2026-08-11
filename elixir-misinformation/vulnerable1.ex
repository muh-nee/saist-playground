def diagnose(conn, symptoms), do: json(conn, %{diagnosis: ReqLLM.generate_text(model: model(), prompt: symptoms).content})
