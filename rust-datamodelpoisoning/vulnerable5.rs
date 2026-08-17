use axum::{extract::Query, routing::get, Router};
use candle_core::{Device, safetensors};
use serde::Deserialize;

#[derive(Deserialize)]
struct LoadParams {
    model_url: String,
}

async fn load_handler(Query(params): Query<LoadParams>) -> String {
    let bytes = reqwest::get(&params.model_url)
        .await
        .unwrap()
        .bytes()
        .await
        .unwrap();
    let device = Device::Cpu;
    let tensors = safetensors::load_buffer(&bytes, &device).unwrap();
    format!("loaded {} tensors", tensors.len())
}

#[tokio::main]
async fn main() {
    let app = Router::new().route("/load", get(load_handler));
    axum::Server::bind(&"0.0.0.0:3000".parse().unwrap())
        .serve(app.into_make_service())
        .await
        .unwrap();
}
