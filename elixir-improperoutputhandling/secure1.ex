def execute(response) do
  action = ReqLLM.text(response) |> Jason.decode!() |> Actions.validate!()
  Actions.execute_allowlisted!(action)
end
