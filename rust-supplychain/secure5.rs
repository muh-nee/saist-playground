use once_cell::sync::Lazy;

static MODULE: Lazy<tch::CModule> = Lazy::new(|| {
    tch::CModule::load("./models/resnet50.pt").expect("failed to load model")
});

fn classify(input: tch::Tensor) -> tch::Tensor {
    MODULE.forward_ts(&[input]).unwrap()
}
