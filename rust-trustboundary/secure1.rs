async fn login(user: AuthenticatedUser, session: Session, pool: PgPool) -> Result<(), Error> {
    let identity = load_identity(user.id, &pool).await?;
    session.insert("user_id", identity.id)?;
    session.insert("role", identity.role)?;
    Ok(())
}
