def advise(conn, question) do
  answer = ExOpenAI.chat_completion(question) |> Grounding.attach_verified_sources()
  json(conn, %{legal_answer: answer, disclaimer: "AI-generated draft; consult a qualified professional."})
end
