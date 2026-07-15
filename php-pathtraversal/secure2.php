<?php
// Allowlist of permitted modules — no dynamic path construction
function loadModule() {
    $allowed = ['home', 'about', 'contact', 'products'];
    $module = $_GET['module'];
    if (!in_array($module, $allowed, true)) {
        http_response_code(404);
        exit;
    }
    include("modules/" . $module . ".php"); // SAFE — strict allowlist, no traversal possible
}
