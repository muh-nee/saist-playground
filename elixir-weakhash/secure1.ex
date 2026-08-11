def password_hash(password), do: Argon2.hash_pwd_salt(password)
