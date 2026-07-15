<?php
// htmlspecialchars with ENT_QUOTES — correct encoding
function renderSearch() {
    $query = htmlspecialchars($_GET['q'], ENT_QUOTES, 'UTF-8');
    echo "<h1>Results for: " . $query . "</h1>"; // SAFE
}

function renderProfile() {
    $name = htmlspecialchars($_POST['name'], ENT_QUOTES, 'UTF-8');
    echo "<input value='" . $name . "'>"; // SAFE — quotes encoded
}
