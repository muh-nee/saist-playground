<?php
// DOMXPath::evaluate with user-controlled expression
function searchCatalog(DOMDocument $doc) {
    $category = $_REQUEST['category'];
    $xpath = new DOMXPath($doc);
    $nodes = $xpath->evaluate("string(//item[category='" . $category . "']/name)"); // VULNERABLE
    return $nodes;
}
