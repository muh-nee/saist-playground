<?php
// call_user_func with user-controlled callback
function dispatch() {
    $action = $_GET['action'];
    $arg = $_GET['arg'];
    call_user_func($action, $arg); // VULNERABLE — attacker can call any PHP function
}

function applyFilter() {
    $filter = $_POST['filter'];
    $data = $_POST['data'];
    return call_user_func_array($filter, [$data]); // VULNERABLE
}
