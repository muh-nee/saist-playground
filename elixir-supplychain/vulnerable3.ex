def load_model(url) do
  {:ok, %{body: bytes}} = HTTPoison.get(url)
  Axon.deserialize(bytes)
end
