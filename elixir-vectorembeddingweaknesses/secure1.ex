def search(query_vector, account), do: Repo.all(from d in Document, where: d.account_id == ^account.id, order_by: l2_distance(d.embedding, ^query_vector))
