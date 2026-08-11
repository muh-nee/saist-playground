def agent(conn, params), do: LangChain.run(tool_enabled_chain(), user_input: params["request"])
