use axum::{extract::Json, routing::post, Router};
use ort::Session;
use serde::Deserialize;

#[derive(Deserialize)]
struct LoadReq {
    model_path: String,
}

async fn load_handler(Json(body): Json<LoadReq>) -> String {
    let session = Session::builder()
        .unwrap()
        .commit_from_file(&body.model_path)
        .unwrap();
    format!("inputs: {:?}", session.inputs.iter().map(|i| &i.name).collect::<Vec<_>>())
}

#[tokio::main]
async fn main() {
    let app = Router::new().route("/load", post(load_handler));
    axum::Server::bind(&"0.0.0.0:3000".parse().unwrap())
        .serve(app.into_make_service())
        .await
        .unwrap();
}
