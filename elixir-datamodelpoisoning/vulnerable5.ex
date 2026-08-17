defmodule AppWeb.TrainingController do
  use AppWeb, :controller

  def train_from_url(conn, %{"dataset_url" => url}) do
    %{body: body} = Req.get!(url)
    dataset = Jason.decode!(body)
    Axon.Loop.run(model(), dataset, optimizer())
    json(conn, %{status: "trained"})
  end

  defp model, do: Axon.input("data", shape: {nil, 10})
  defp optimizer, do: Axon.Optimizers.adam(0.001)
end
