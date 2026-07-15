<?php
// Unencoded echo of GET parameter — most common PHP XSS
function renderSearch() {
    $query = $_GET['q'];
    echo "<h1>Results for: " . $query . "</h1>"; // VULNERABLE
}

function showError() {
    $msg = $_GET['error'];
    echo "<p class='error'>" . $msg . "</p>"; // VULNERABLE
}
