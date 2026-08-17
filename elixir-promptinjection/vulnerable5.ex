def agent_turn(conn, %{"query" => query}) do
  {:ok, tool_result} = MCP.Client.call_tool(client(), "web_search", %{query: query})
  tool_output = tool_result.content
  ReqLLM.generate_text(model: model(), prompt: system_prompt() <> tool_output)
  json(conn, %{ok: true})
end
