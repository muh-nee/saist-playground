def agent, do: LangChain.Agent.new(tools: [function_call(name: "delete_account", function: &Accounts.delete/1)])
