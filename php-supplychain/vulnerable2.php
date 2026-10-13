<?php
// Rubix model fetched from attacker-controlled URL and loaded without any integrity check
use Rubix\ML\PersistentModel;
use Rubix\ML\Persisters\Filesystem;

function loadRemoteClassifier(string $modelUrl): PersistentModel {
    $tmp = tempnam(sys_get_temp_dir(), 'mdl');
    // VULNERABLE: fetched over plain HTTP(S) with no SHA/signature verification
    file_put_contents($tmp, file_get_contents($modelUrl));
    // Rubix persistence uses PHP serialize() internally — loading attacker bytes is RCE
    return PersistentModel::load(new Filesystem($tmp));
}
