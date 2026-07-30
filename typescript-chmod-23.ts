// Sets overly permissive file mode bits (world read/write/execute) on a temp file.
const fs = require('fs');
const fsPromises = fs.promises;

// Synchronous chmod call — should be flagged by the chmod permission rule.
fs.chmodSync("/tmp/myfile", 0o777);

// Async/promise-based chmod call — same permissive mode, different API surface.
fsPromises.chmod("/tmp/fsPromises", 0o777);
