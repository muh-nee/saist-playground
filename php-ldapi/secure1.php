<?php
// ldap_escape() with LDAP_ESCAPE_FILTER — correct escaping
function findUser($ldap) {
    $username = ldap_escape($_POST['username'], '', LDAP_ESCAPE_FILTER);
    $filter = "(uid=" . $username . ")"; // SAFE — metacharacters escaped
    $result = ldap_search($ldap, "dc=example,dc=com", $filter);
    return ldap_get_entries($ldap, $result);
}

function bindUser($conn) {
    $cn = ldap_escape($_POST['cn'], '', LDAP_ESCAPE_DN);
    $dn = "cn=" . $cn . ",dc=example,dc=com"; // SAFE — DN component escaped
    return ldap_bind($conn, $dn, $_POST['password']);
}
