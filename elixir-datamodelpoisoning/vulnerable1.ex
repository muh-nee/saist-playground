defmodule AppWeb.TrainingController do
  use AppWeb, :controller

  def train(conn, %{"dataset" => dataset}) do
    Axon.Loop.run(model(), dataset, optimizer())
    json(conn, %{status: "trained"})
  end

  defp model, do: Axon.input("data", shape: {nil, 10})
  defp optimizer, do: Axon.Optimizers.adam(0.001)
end
