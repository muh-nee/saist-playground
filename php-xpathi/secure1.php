<?php
// Strict alphanumeric allowlist before XPath interpolation
function findUser(DOMDocument $doc) {
    $username = $_GET['user'];
    if (!preg_match('/^[a-zA-Z0-9_]+$/', $username)) {
        http_response_code(400);
        exit;
    }
    $xpath = new DOMXPath($doc);
    return $xpath->query("//user[name='" . $username . "']"); // SAFE — only alphanumeric
}
