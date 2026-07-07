import express, { Request, Response } from 'express';
import pino from 'pino';

const logger = pino();

const app = express();
app.use(express.json());

app.post('/action', (req: Request, res: Response): void => {
  // VULNERABLE: request body field cast to string and passed directly as the log message
  logger.warn(req.body.action as string);
  res.sendStatus(202);
});

app.listen(3000);
