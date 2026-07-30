// File contains an http.createServer call that triggers custom security error

import http from 'http';
import express, { Express } from 'express';

const app: Express = express();

var httpServer = http.createServer(app)
httpServer.listen(8080);
