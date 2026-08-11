async fn greet(Query(query): Query<Greeting>) -> Result<Html<String>, Error> {
    let page = GreetingTemplate { name: &query.name };
    Ok(Html(page.render()?))
}
