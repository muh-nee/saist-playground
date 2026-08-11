def checksum(bytes), do: :crypto.hash(:md5, bytes)
