// Spins up an Express app behind a plain http.Server — no TLS termination here.
// A production deployment should wrap this in https.createServer with a cert/key pair.

import http from 'http';
import express, { Express } from 'express';

const app: Express = express();

// Insecure: cleartext HTTP on port 8080.
var httpServer = http.createServer(app)
httpServer.listen(8080);
