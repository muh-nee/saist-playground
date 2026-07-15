<?php
// print_r and debug_backtrace in HTTP response
function renderDebug($data) {
    echo "<pre>";
    print_r($data); // VULNERABLE — exposes object internals
    echo "</pre>";
}

function showTrace() {
    debug_print_backtrace(); // VULNERABLE — call stack exposed in response
}
