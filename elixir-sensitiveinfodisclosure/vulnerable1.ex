def summarize(user), do: ReqLLM.generate_text(model: model(), prompt: "Summarize #{user.api_key}")
