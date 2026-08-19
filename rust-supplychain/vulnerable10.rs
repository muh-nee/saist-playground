use hf_hub::{api::sync::Api, Repo, RepoType};
use candle_core::{Device, safetensors};

fn load_with_mutable_revision(device: &Device) -> Result<(), Box<dyn std::error::Error>> {
    let api = Api::new()?;
    let repo = Repo::with_revision(
        "org/my-model".to_string(),
        RepoType::Model,
        "main".to_string(),
    );
    let path = api.repo(repo).get("model.safetensors")?;
    let _tensors = safetensors::load(&path, device)?;
    Ok(())
}
