import logging


def format_query_preview(comparison_params: dict[str, str]) -> str:
    output_path = comparison_params["explicit_output_path"]
    snippet = f"SELECT * FROM TABLE(load('{output_path}'))"
    logging.info("Run this query manually in the data console:\n%s", snippet)
    return snippet
