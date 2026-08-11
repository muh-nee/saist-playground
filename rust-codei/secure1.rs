async fn calculate(Json(body): Json<OperationRequest>) -> Result<i64, Error> {
    match body.operation.as_str() { "sum" => Ok(body.values.iter().sum()), "max" => body.values.iter().max().copied().ok_or(Error::InvalidInput), _ => Err(Error::InvalidOperation) }
}
