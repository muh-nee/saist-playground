<?php
// Legacy mysql_query and MySQLi exec with concatenation
function legacyGetUser($id) {
    $result = mysql_query("SELECT * FROM users WHERE id = " . $id); // VULNERABLE
    return mysql_fetch_assoc($result);
}

function execRaw($conn, $table, $value) {
    $sql = "DELETE FROM " . $table . " WHERE value = '" . $value . "'";
    $conn->query($sql); // VULNERABLE
}
