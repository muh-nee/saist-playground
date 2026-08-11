async fn delete_project(user: CurrentUser, Path(project_id): Path<i64>, pool: PgPool) -> Result<(), Error> {
    authorize_project_admin(&user, project_id, &pool).await?;
    sqlx::query!("DELETE FROM projects WHERE id = $1", project_id).execute(&pool).await?;
    Ok(())
}
