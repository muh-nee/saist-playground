def summarize(document), do: ReqLLM.generate_text(model: model(), prompt: redact_secrets(document))
