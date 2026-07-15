<?php
// WordPress $wpdb without prepare()
function getPostBySlug($slug) {
    global $wpdb;
    $results = $wpdb->get_results("SELECT * FROM wp_posts WHERE post_name = '$slug'"); // VULNERABLE
    return $results;
}

function getUserMeta($user_id, $meta_key) {
    global $wpdb;
    return $wpdb->get_var("SELECT meta_value FROM wp_usermeta WHERE user_id = $user_id AND meta_key = '$meta_key'"); // VULNERABLE
}
