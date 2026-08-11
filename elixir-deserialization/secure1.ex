def decode(payload), do: :erlang.binary_to_term(payload, [:safe])
