<?php
// var_dump and phpinfo in accessible endpoints
function debugEndpoint() {
    $data = $_GET['id'];
    $result = fetch_from_db($data);
    var_dump($result); // VULNERABLE — internal structure exposed
}

// info.php accessible in production
phpinfo(); // VULNERABLE — full PHP/server config disclosed
