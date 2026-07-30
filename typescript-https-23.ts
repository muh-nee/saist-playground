// File contains an http.createServer call that triggers custom security error.
// Using plain HTTP instead of HTTPS means traffic isn't encrypted in transit.

import http from 'http';
import express, { Express } from 'express';

const app: Express = express();

// Plain HTTP server — no TLS, should trip the insecure-transport rule.
var httpServer = http.createServer(app)
httpServer.listen(8080);
