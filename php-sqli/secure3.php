<?php
// WordPress $wpdb->prepare() — parameterized WordPress queries
function getPostBySlug($slug) {
    global $wpdb;
    $results = $wpdb->get_results(
        $wpdb->prepare("SELECT * FROM wp_posts WHERE post_name = %s", $slug) // SAFE
    );
    return $results;
}

function getUserMeta($user_id, $meta_key) {
    global $wpdb;
    return $wpdb->get_var(
        $wpdb->prepare(
            "SELECT meta_value FROM wp_usermeta WHERE user_id = %d AND meta_key = %s",
            $user_id,
            $meta_key
        ) // SAFE
    );
}
