import express, { Request, Response } from 'express';

const app = express();

app.get('/search', (req: Request, res: Response): void => {
  // SAFE: CRLF characters stripped before the value is included in the log message
  const sanitized = (req.query.q as string).replace(/[\r\n]/g, '');
  console.log('Query: ' + sanitized);
  res.json({ results: [] });
});

app.listen(3000);
