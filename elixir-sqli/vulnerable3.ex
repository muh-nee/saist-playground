def delete(conn, params), do: Postgrex.query!(connection(), "DELETE FROM sessions WHERE user_id = #{params["id"]}", [])
