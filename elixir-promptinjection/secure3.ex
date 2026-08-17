def agent_turn(conn, %{"query" => query}) do
  {:ok, tool_result} = MCP.Client.call_tool(client(), "search", %{query: query})
  result_count = Map.fetch!(tool_result, "result_count")
  true = is_integer(result_count)
  safe_content = "Found #{result_count} results"
  ReqLLM.generate_text(model: model(), prompt: system_prompt() <> "\n" <> safe_content)
  json(conn, %{ok: true})
end
