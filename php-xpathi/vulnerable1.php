<?php
// User input concatenated into XPath expression
function findUser(DOMDocument $doc) {
    $username = $_GET['user'];
    $xpath = new DOMXPath($doc);
    $result = $xpath->query("//user[name='" . $username . "']"); // VULNERABLE
    return $result;
}

function getPrice(SimpleXMLElement $xml) {
    $id = $_POST['product_id'];
    return $xml->xpath("//product[@id='" . $id . "']/price"); // VULNERABLE
}
