<?php
// GPG-verify a signed model bundle before loading
use Rubix\ML\PersistentModel;
use Rubix\ML\Persisters\Filesystem;

const TRUSTED_SIGNERS = ['BA0D 7F5B 1234 ABCD'];

function loadSignedClassifier(string $bundlePath, string $sigPath): PersistentModel {
    // SAFE: verify the detached GPG signature with a known trusted keyring before load
    $cmd = sprintf(
        'gpg --status-fd 1 --no-default-keyring --keyring %s --verify %s %s',
        escapeshellarg(__DIR__ . '/trusted.gpg'),
        escapeshellarg($sigPath),
        escapeshellarg($bundlePath)
    );
    $out = []; $rc = 1;
    exec($cmd, $out, $rc);
    $joined = implode("\n", $out);
    $ok = $rc === 0 && array_filter(TRUSTED_SIGNERS, static fn (string $fp) => str_contains($joined, $fp));
    if (!$ok) {
        throw new RuntimeException('model signature not from a trusted signer');
    }
    return PersistentModel::load(new Filesystem($bundlePath));
}
