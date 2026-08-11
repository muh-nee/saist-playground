def encode(conn, params) do
  size = String.to_integer(params["size"])
  true = size in 0..4_294_967_295
  <<size::unsigned-integer-size(32)>>
end
