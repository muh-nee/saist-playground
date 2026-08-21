use hf_hub::{api::sync::Api, Repo, RepoType};
use candle_core::{Device, safetensors};

fn load_pinned(device: &Device) -> Result<(), Box<dyn std::error::Error>> {
    let api = Api::new()?;
    let repo = Repo::with_revision(
        "org/my-classifier".to_string(),
        RepoType::Model,
        "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2".to_string(),
    );
    let path = api.repo(repo).get("model.safetensors")?;
    let _tensors = safetensors::load(&path, device)?;
    Ok(())
}

