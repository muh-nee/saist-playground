<?php
// Twig auto-escaping — template engine handles encoding
function renderPage($loader) {
    $twig = new \Twig\Environment($loader, ['autoescape' => 'html']);
    $name = $_GET['name'];
    echo $twig->render('page.html.twig', ['name' => $name]); // SAFE — Twig auto-escapes
}

function jsonResponse() {
    // Output as JSON — XSS not applicable in JSON context with correct Content-Type
    $data = ['query' => $_GET['q']];
    header('Content-Type: application/json');
    echo json_encode($data); // SAFE
}
