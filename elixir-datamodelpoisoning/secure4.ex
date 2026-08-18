defmodule AppWeb.TrainingController do
  use AppWeb, :controller

  @trusted_url "https://internal.example.com/datasets/approved.json"
  @expected_hash "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

  def train_from_trusted_url(conn, _params) do
    %{body: body} = Req.get!(@trusted_url)
    actual = :crypto.hash(:sha256, body) |> Base.encode16(case: :lower)
    if actual == @expected_hash do
      dataset = Jason.decode!(body)
      Axon.Loop.run(model(), dataset, optimizer())
      json(conn, %{status: "trained"})
    else
      send_resp(conn, 403, "integrity check failed")
    end
  end

  defp model, do: Axon.input("data", shape: {nil, 10})
  defp optimizer, do: Axon.Optimizers.adam(0.001)
end
