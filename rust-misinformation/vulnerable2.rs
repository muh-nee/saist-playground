async fn medical_advice(Query(query): Query<Question>, client: Client) -> Result<String, Error> {
    client.chat(format!("Give medical advice: {}", query.question)).await.map_err(Error::from)
}
