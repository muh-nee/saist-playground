def load_model(url) do
  {:ok, %{body: bytes}} = Req.get(url)
  true = :crypto.hash(:sha256, bytes) == @expected_model_digest
  Nx.deserialize(bytes)
end
