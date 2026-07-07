import express, { Request, Response } from 'express';
import winston from 'winston';

const logger = winston.createLogger({
  transports: [new winston.transports.Console()],
});

interface LoginBody {
  username: string;
  password: string;
}

const app = express();
app.use(express.json());

app.post('/login', (req: Request<{}, {}, LoginBody>, res: Response): void => {
  // VULNERABLE: typed body field concatenated directly into log message string
  logger.info('Login attempt for user: ' + req.body.username);
  res.sendStatus(200);
});

app.listen(3000);
