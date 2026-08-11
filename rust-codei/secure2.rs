use rhai::Engine;

fn run_fixed_script() -> Result<(), Error> {
    Engine::new().run("let result = 2 + 2;").map_err(Error::from)
}
