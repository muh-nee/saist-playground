def render(conn, %{"template" => template}), do: EEx.eval_string(template, assigns: conn.assigns)
