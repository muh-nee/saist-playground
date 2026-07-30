// Grants world read/write/execute permissions on a file — classic overly permissive chmod.
const fs = require('fs');
const fsPromises = fs.promises;

// NOTE: 0o777 is the worst-case mode; a real fix would scope this down (e.g. 0o644).
fs.chmodSync("/tmp/myfile", 0o777);

// Same issue via the promise-based fs API instead of the sync one.
fsPromises.chmod("/tmp/fsPromises", 0o777);
