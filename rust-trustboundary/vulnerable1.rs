async fn login(Json(body): Json<LoginRequest>, session: Session) -> Result<(), Error> {
    session.insert("user_id", body.user_id)?;
    session.insert("role", body.role)?;
    Ok(())
}
