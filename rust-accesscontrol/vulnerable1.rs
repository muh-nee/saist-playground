use axum::{extract::Path, Json};

async fn get_invoice(Path(invoice_id): Path<i64>, pool: PgPool) -> Result<Json<Invoice>, Error> {
    let invoice = sqlx::query_as!(Invoice, "SELECT * FROM invoices WHERE id = $1", invoice_id)
        .fetch_one(&pool)
        .await?;
    Ok(Json(invoice))
}
