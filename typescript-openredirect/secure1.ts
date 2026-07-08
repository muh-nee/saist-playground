import express, { Request, Response } from 'express';

const app = express();

const ALLOWED_REDIRECTS = new Set<string>(['/dashboard', '/profile', '/settings', '/home']);

// Safe: allowlist of permitted redirect paths
app.post('/login', (req: Request, res: Response) => {
  const { username, password } = req.body as { username: string; password: string };
  let next: string = req.query.next as string;

  if (authenticate(username, password)) {
    // Only redirect to explicitly allowed destinations
    if (!ALLOWED_REDIRECTS.has(next)) {
      next = '/dashboard';
    }
    res.redirect(next);
  } else {
    res.status(401).send('Unauthorized');
  }
});

function authenticate(username: string, password: string): boolean {
  return username === 'admin' && password === 'secret';
}

app.listen(3000);
