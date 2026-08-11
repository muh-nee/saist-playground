def search(query_vector), do: Pgvector.query("SELECT * FROM documents ORDER BY embedding <=> $1", [query_vector])
