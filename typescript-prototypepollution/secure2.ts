import express, { Request, Response } from 'express';

const app = express();
app.use(express.json());

// Safe: Object.create(null) as merge target — no prototype to pollute
app.post('/settings', (req: Request, res: Response) => {
  // Object.create(null) creates an object with no prototype chain
  // Even if req.body contains __proto__, there is no Object.prototype to pollute
  const config = Object.create(null) as Record<string, unknown>;
  Object.assign(config, req.body);
  applyConfig(config);
  res.json({ success: true });
});

function applyConfig(cfg: Record<string, unknown>): void {
  // config is applied server-wide
}

app.listen(3000);
