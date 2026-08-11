use mlua::Lua;

fn run_uploaded_script(script: String) -> Result<(), Error> {
    Lua::new().load(&script).exec().map_err(Error::from)
}
