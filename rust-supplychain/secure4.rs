use once_cell::sync::Lazy;
use ort::Session;

static SESSION: Lazy<Session> = Lazy::new(|| {
    Session::builder().unwrap().commit_from_file("./models/classifier.onnx").unwrap()
});

async fn infer(features: Vec<f32>) -> String {
    format!("session loaded: {}", SESSION.inputs().len())
}
