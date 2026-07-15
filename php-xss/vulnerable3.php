<?php
// Short-echo and printf with user input
function renderPage() {
    $title = $_GET['title'];
    ?>
    <title><?= $title ?></title>
    <?php
    // VULNERABLE — short echo without encoding

    $name = $_GET['name'];
    printf("<h1>Hello, %s!</h1>", $name); // VULNERABLE
}
