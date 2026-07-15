<?php
// yaml_parse() with user-controlled YAML — object injection
function parseConfig() {
    $yaml = $_POST['config'];
    $config = yaml_parse($yaml); // VULNERABLE — YAML can instantiate arbitrary PHP objects
    return $config;
}

// Phar stream wrapper triggers deserialization on file operations
function checkFile() {
    $path = $_GET['path'];
    file_exists($path); // VULNERABLE if path is phar:// — triggers phar deserialization
}
