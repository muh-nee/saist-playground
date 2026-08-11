fn compile_wasm(bytes: &[u8]) -> Result<wasmtime::Module, Error> {
    wasmtime::Module::new(engine(), bytes).map_err(Error::from)
}
