def chat(conn, params) do
  response = ReqLLM.generate_text(model: model(), prompt: params["message"])
  execute_authorized_action!(validate_tool_call!(response.tool_calls, conn.assigns.current_user))
end
