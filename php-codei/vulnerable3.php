<?php
// assert() with string argument (PHP < 8)
function validateInput() {
    $check = $_POST['check'];
    assert($check); // VULNERABLE — string evaluated as PHP expression in PHP < 8
}

// create_function (removed in PHP 8, still in older codebases)
function makeTransformer() {
    $body = $_GET['body'];
    $fn = create_function('$x', $body); // VULNERABLE — eval() equivalent
    return $fn(42);
}
