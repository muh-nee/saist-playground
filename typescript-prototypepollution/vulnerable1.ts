import express, { Request, Response } from 'express';

const app = express();
app.use(express.json());

interface Settings {
  theme: string;
  lang: string;
}

// Vulnerable: TypeScript cast (as Settings) is compile-time only — does NOT sanitize __proto__ at runtime
app.post('/settings', (req: Request, res: Response) => {
  const config: Settings = {} as Settings;
  Object.assign(config, req.body);
  // An attacker can send: {"__proto__": {"isAdmin": true}}
  // The TypeScript type cast does not prevent __proto__ from being in req.body at runtime
  res.json({ success: true });
});

app.listen(3000);
