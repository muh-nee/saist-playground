def agent(user), do: ReqLLM.Agent.new(tools: [tool(name: "lookup_order", function: fn id -> Orders.lookup_authorized(user, id) end)])
