<?php
// User-uploaded Rubix model path is loaded directly (unserialize gadget risk + poisoned weights)
use Rubix\ML\PersistentModel;
use Rubix\ML\Persisters\Filesystem;

function predict(array $sample): mixed {
    $uploaded = $_FILES['model']['tmp_name']; // attacker-controlled bytes
    // VULNERABLE: Filesystem persister unserializes arbitrary PHP objects from the uploaded file
    $model = PersistentModel::load(new Filesystem($uploaded));
    return $model->predict([$sample])[0];
}
