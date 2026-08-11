fn delete_user(id: String, conn: &mut PgConnection) -> Result<(), Error> {
    diesel::sql_query(format!("DELETE FROM users WHERE id = {id}")).execute(conn)?;
    Ok(())
}
