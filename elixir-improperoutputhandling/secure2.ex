def render(response), do: ExOpenAI.message_content(response) |> Phoenix.HTML.html_escape()
