def password_hash(password), do: :crypto.hash(:md5, password)
