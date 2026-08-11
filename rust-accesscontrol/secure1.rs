async fn get_invoice(user: CurrentUser, Path(invoice_id): Path<i64>, pool: PgPool) -> Result<Invoice, Error> {
    sqlx::query_as!(Invoice, "SELECT * FROM invoices WHERE id = $1 AND owner_id = $2", invoice_id, user.id)
        .fetch_one(&pool)
        .await
        .map_err(Error::from)
}
