<?php
// XPath with no user input in expression — fixed query, user input used for post-filtering only
function getProducts(SimpleXMLElement $xml) {
    $items = $xml->xpath("//product"); // SAFE — no user input in expression
    $category = $_GET['category'];

    return array_filter((array)$items, function($item) use ($category) {
        return (string)$item->category === $category; // filter in PHP, not XPath
    });
}
