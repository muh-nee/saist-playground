async fn fine_tune(Json(body): Json<TrainingRequest>, trainer: Trainer) -> Result<(), Error> {
    trainer.train(body.examples).await?;
    Ok(())
}
