use rand::{distr::Alphanumeric, Rng};

fn reset_token() -> String {
    rand::rng().sample_iter(Alphanumeric).take(32).map(char::from).collect()
}
