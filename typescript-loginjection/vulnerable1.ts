import express, { Request, Response } from 'express';

const app = express();

app.get('/search', (req: Request, res: Response): void => {
  const query = req.query.q as string;
  // VULNERABLE: user-controlled data interpolated directly into log message string
  console.log(`Search query: ${query}`);
  res.json({ results: [] });
});

app.listen(3000);
