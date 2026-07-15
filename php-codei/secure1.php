<?php
// Allowlisted dispatch — no dynamic code execution
function dispatch() {
    $allowed = ['formatDate', 'sanitizeText', 'truncateString'];
    $action = $_GET['action'];
    if (!in_array($action, $allowed, true)) {
        http_response_code(400);
        exit;
    }
    call_user_func($action, $_GET['arg']); // SAFE — strict allowlist
}
