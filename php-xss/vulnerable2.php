<?php
// Insufficient sanitization — strip_tags is bypassable in attribute context
function renderProfile() {
    $name = strip_tags($_POST['name']);
    echo "<input value='" . $name . "'>"; // VULNERABLE — strip_tags doesn't escape quotes
}

function renderComment() {
    $comment = html_entity_decode($_POST['comment']);
    echo "<div>" . $comment . "</div>"; // VULNERABLE — html_entity_decode reverses encoding
}
