import express, { Request, Response } from 'express';

const app = express();
app.use(express.json());

// Vulnerable: dynamic key assignment from typed request body — TypeScript type does NOT sanitize at runtime
app.post('/update', (req: Request, res: Response) => {
  const store: Record<string, unknown> = {};
  const key = req.body.key as string;   // TypeScript cast does NOT sanitize __proto__ at runtime
  const value = req.body.value;
  store[key] = value;                   // pollutes Object.prototype if key is __proto__
  res.json({ ok: true });
});

app.listen(3000);
