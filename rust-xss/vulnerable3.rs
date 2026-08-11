use maud::PreEscaped;

fn render_comment(comment: String) -> PreEscaped<String> {
    PreEscaped(comment)
}
