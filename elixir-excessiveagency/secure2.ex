def agent(user), do: LangChain.Agent.new(tools: [function_call(name: "draft_refund", function: fn id -> Refunds.draft(user, id) end)], execute: :after_confirmation)
