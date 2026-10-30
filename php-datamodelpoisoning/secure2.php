<?php
// Only load models from a read-only internal registry; verify SHA256 recorded in the registry
use Rubix\ML\PersistentModel;
use Rubix\ML\Persisters\Filesystem;

final class ModelRegistry
{
    /** @var array<string, array{path:string, sha256:string}> */
    private const ENTRIES = [
        'risk-scorer' => [
            'path'   => '/opt/models/risk-scorer-20251005.rbx',
            'sha256' => 'a1b2c3d4e5f6071829384756657483291102030405060708090a0b0c0d0e0f10',
        ],
    ];

    public static function load(string $name): PersistentModel
    {
        if (!array_key_exists($name, self::ENTRIES)) {
            throw new InvalidArgumentException('unknown model name');
        }
        $meta  = self::ENTRIES[$name];
        $bytes = file_get_contents($meta['path']);
        if ($bytes === false || !hash_equals($meta['sha256'], hash('sha256', $bytes))) {
            throw new RuntimeException('model integrity check failed');
        }
        return PersistentModel::load(new Filesystem($meta['path']));
    }
}

