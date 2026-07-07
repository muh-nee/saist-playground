import express, { Request, Response } from 'express';
import winston from 'winston';

const logger = winston.createLogger({
  transports: [new winston.transports.Console()],
});

const app = express();
app.use(express.json());

app.post('/pay/:userId', (req: Request, res: Response): void => {
  const userId: string = req.params.userId;
  try {
    throw new Error('Payment gateway timeout');
  } catch (err) {
    const error = err as Error;
    // VULNERABLE: userId and err.message concatenated directly into log message string
    logger.error('Exception for: ' + userId + ' - ' + error.message);
    res.sendStatus(500);
  }
});

app.listen(3000);
