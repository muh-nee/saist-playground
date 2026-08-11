def chat(conn, params), do: ReqLLM.generate_text(model: model(), prompt: system_prompt() <> params["message"])
