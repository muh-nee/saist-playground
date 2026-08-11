async fn load_cached_module(Path(path): Path<String>) -> Result<wasmtime::Module, Error> {
    unsafe { wasmtime::Module::deserialize_file(engine(), path).map_err(Error::from) }
}
