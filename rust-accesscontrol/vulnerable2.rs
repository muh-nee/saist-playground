async fn delete_project(user: CurrentUser, Path(project_id): Path<i64>, pool: PgPool) -> Result<(), Error> {
    sqlx::query!("DELETE FROM projects WHERE id = $1", project_id)
        .execute(&pool)
        .await?;
    Ok(())
}
