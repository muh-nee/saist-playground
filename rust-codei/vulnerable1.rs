use rhai::{Dynamic, Engine};

async fn evaluate(Json(body): Json<ScriptRequest>) -> Result<Dynamic, Error> {
    Engine::new().eval::<Dynamic>(&body.script).map_err(Error::from)
}
