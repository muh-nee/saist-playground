def query(response), do: Repo.query(ExOpenAI.message_content(response))
