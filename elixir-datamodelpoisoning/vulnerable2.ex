defmodule AppWeb.FinetuneController do
  use AppWeb, :controller

  def fine_tune(conn, %{"records" => training_records}) do
    Nx.Training.run(model(), training_records)
    json(conn, %{status: "fine-tuned"})
  end

  defp model, do: Axon.input("data", shape: {nil, 10})
end
