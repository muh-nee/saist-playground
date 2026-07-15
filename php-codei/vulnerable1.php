<?php
// eval() with user input — critical RCE
function calculate() {
    $expr = $_GET['expr'];
    eval('$result = ' . $expr . ';'); // VULNERABLE — arbitrary code execution
    return $result;
}

function renderTemplate() {
    $template = $_POST['template'];
    eval("?>" . $template . "<?php"); // VULNERABLE
}
