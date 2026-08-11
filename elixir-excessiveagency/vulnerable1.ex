def agent, do: ReqLLM.Agent.new(tools: [tool(name: "run_command", function: &System.cmd/2)])
