def generate(prompt), do: ReqLLM.generate_text(model: model(), prompt: prompt)
