<?php
// header('Location:') with unvalidated user-supplied URL
function redirect() {
    $url = $_GET['redirect'];
    header("Location: " . $url); // VULNERABLE — attacker supplies //evil.com
    exit;
}

function postLoginRedirect() {
    $next = $_POST['next'];
    header('Location: ' . $next); // VULNERABLE
    exit;
}
