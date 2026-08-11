def debug(conn, _params) do
  system_message = %{role: :system, content: system_prompt()}
  send_resp(conn, 200, system_message.content)
end
