def render(response), do: Phoenix.HTML.raw(LangChain.Message.content(response))
