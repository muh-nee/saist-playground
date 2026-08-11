def generate(prompt), do: ReqLLM.generate_text(model: model(), prompt: prompt, max_tokens: 500, timeout: 10_000)
