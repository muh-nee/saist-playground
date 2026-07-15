<?php
// Alphanumeric allowlist validation before LDAP use
function findUser($ldap) {
    $username = $_POST['username'];
    if (!preg_match('/^[a-zA-Z0-9._-]+$/', $username)) {
        http_response_code(400);
        exit;
    }
    $filter = "(uid=" . $username . ")"; // SAFE — only safe characters allowed
    $result = ldap_search($ldap, "dc=example,dc=com", $filter);
    return ldap_get_entries($ldap, $result);
}
