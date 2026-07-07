import express, { Request, Response } from 'express';
import winston from 'winston';

const logger = winston.createLogger({
  transports: [new winston.transports.Console()],
});

interface LoginBody {
  username: string;
}

const app = express();
app.use(express.json());

app.post('/login', (req: Request<{}, {}, LoginBody>, res: Response): void => {
  // SAFE: user value is scoped to a child logger's metadata context, message string is fixed
  logger.child({ user: req.body.username }).info('login_attempt');
  res.sendStatus(200);
});

app.listen(3000);
