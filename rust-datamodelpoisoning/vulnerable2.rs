async fn load_module(Query(query): Query<ModelQuery>) -> Result<tch::CModule, Error> {
    tch::CModule::load(query.path).map_err(Error::from)
}
