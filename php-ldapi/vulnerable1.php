<?php
// User input interpolated directly into LDAP filter
function findUser($ldap) {
    $username = $_POST['username'];
    $filter = "(uid=" . $username . ")"; // VULNERABLE — attacker injects )(uid=*
    $result = ldap_search($ldap, "dc=example,dc=com", $filter);
    return ldap_get_entries($ldap, $result);
}

function authenticate($conn) {
    $user = $_GET['user'];
    $pass = $_GET['pass'];
    $filter = "(&(uid=$user)(userPassword=$pass))"; // VULNERABLE — filter injection
    return ldap_search($conn, "ou=users,dc=corp,dc=com", $filter);
}
