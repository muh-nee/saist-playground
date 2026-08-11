def load_model(url) do
  {:ok, %{body: bytes}} = Req.get(url)
  Nx.deserialize(bytes)
end
