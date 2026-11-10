import express, { Request, Response, NextFunction } from 'express';

export function globalErrorHandler(
  error: unknown,
  req: Request,
  res: Response,
  next: NextFunction
): void {
  res.status(500).json({ error: String(error) });
}

const app = express();
app.get('/records', (_req, _res, next) => {
  next(new Error('Connection failed for postgres://service:password@internal-db:5432/records'));
});
app.use(globalErrorHandler);
app.listen(3000, '0.0.0.0');
