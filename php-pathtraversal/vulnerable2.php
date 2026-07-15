<?php
// Dynamic include — path traversal + RCE risk
function loadModule() {
    $module = $_GET['module'];
    include("modules/" . $module . ".php"); // VULNERABLE — RCE if attacker controls path
}

function loadPage() {
    $page = $_REQUEST['page'];
    require("pages/" . $page); // VULNERABLE
}
