def ingest(document) do
  embedding = embed(document)
  Qdrant.upsert(collection: "knowledge", vector: embedding, payload: %{text: document})
end
