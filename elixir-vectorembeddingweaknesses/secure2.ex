def ingest(document, account), do: Qdrant.upsert(collection: account.collection, vector: embed(document), payload: %{account_id: account.id, provenance: verified_source(document)})
