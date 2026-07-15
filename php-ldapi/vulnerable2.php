<?php
// User-controlled DN in ldap_bind — authentication bypass
function bindUser($conn) {
    $dn = "cn=" . $_POST['cn'] . ",dc=example,dc=com"; // VULNERABLE — CN not escaped
    $password = $_POST['password'];
    return ldap_bind($conn, $dn, $password);
}

function findGroup($ldap) {
    $group = $_GET['group'];
    $filter = "(cn=" . $group . ")"; // VULNERABLE
    $result = ldap_list($ldap, "ou=groups,dc=corp,dc=com", $filter);
    return ldap_get_entries($ldap, $result);
}
