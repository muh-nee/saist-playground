async fn select_tenant(user: AuthenticatedUser, Query(query): Query<TenantQuery>, session: Session, pool: PgPool) -> Result<(), Error> {
    require_tenant_membership(user.id, &query.tenant, &pool).await?;
    session.insert("tenant", query.tenant)?;
    Ok(())
}
