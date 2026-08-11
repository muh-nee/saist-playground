async fn fine_tune(user: CurrentUser, Json(body): Json<TrainingRequest>, trainer: Trainer) -> Result<(), Error> {
    require_model_admin(&user)?;
    let examples = validate_training_examples(body.examples)?;
    trainer.train(examples).await?;
    Ok(())
}
