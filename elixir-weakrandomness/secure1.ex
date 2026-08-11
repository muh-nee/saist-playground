def token, do: :crypto.strong_rand_bytes(32) |> Base.url_encode64(padding: false)
