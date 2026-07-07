import express, { Request, Response } from 'express';
import pino from 'pino';

const log = pino();

const app = express();
app.use(express.json());

app.post('/login', (req: Request, res: Response): void => {
  // SAFE: user-controlled value is in the structured metadata object, not the message string
  log.info({ user: req.body.username as string }, 'login_attempt');
  res.sendStatus(200);
});

app.listen(3000);
