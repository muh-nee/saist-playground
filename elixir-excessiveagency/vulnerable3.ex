def agent, do: ExOpenAI.agent(tools: [MCP.all_tools()], execute: :automatic)
