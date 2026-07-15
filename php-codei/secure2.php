<?php
// Twig template engine instead of eval
function renderTemplate($loader) {
    $twig = new \Twig\Environment($loader);
    $templateName = $_GET['template'];

    // Validate against known templates — no eval involved
    $allowed = ['home.html.twig', 'about.html.twig'];
    if (!in_array($templateName, $allowed, true)) {
        http_response_code(404);
        exit;
    }
    return $twig->render($templateName, ['user' => 'World']); // SAFE
}
