import express, { Request, Response } from 'express';

const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Vulnerable: user-controlled query parameter passed directly to res.redirect
// TypeScript type annotation does NOT sanitize values at runtime
app.post('/login', (req: Request, res: Response) => {
  const { username, password } = req.body as { username: string; password: string };
  const next: string = req.query.next as string;

  if (authenticate(username, password)) {
    // VULNERABLE: type annotation doesn't prevent //evil.com from being passed
    res.redirect(next);
  } else {
    res.status(401).send('Unauthorized');
  }
});

function authenticate(username: string, password: string): boolean {
  return username === 'admin' && password === 'secret';
}

app.listen(3000);
