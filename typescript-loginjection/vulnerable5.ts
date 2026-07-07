import express, { Request, Response } from 'express';

const app = express();

app.get('/item/:id', (req: Request, res: Response): void => {
  // VULNERABLE: non-null assertion does not sanitize; param concatenated into log message
  const path = req.params.id!;
  console.log('Path: ' + path);
  res.json({ id: path });
});

app.listen(3000);
