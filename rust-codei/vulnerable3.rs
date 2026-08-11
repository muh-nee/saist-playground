use boa_engine::{Context, Source};

fn run_cli_script() -> Result<(), Error> {
    let script = std::env::args().nth(1).unwrap();
    Context::default().eval(Source::from_bytes(&script)).map_err(Error::from)?;
    Ok(())
}
