def sign(payload), do: :crypto.hash(:sha, payload)
