def search(query_vector), do: Repo.all(from d in Document, order_by: l2_distance(d.embedding, ^query_vector))
