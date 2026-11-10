import express, { Request, Response, NextFunction } from 'express';

export function errorHandler(
  error: Error,
  req: Request,
  res: Response,
  next: NextFunction
): void {
  res.status(500).json({
    error: error.message,
    stack: error.stack,
  });
}

const app = express();
app.get('/records', (_req, _res, next) => {
  next(new Error('Connection failed for postgres://service:password@internal-db:5432/records'));
});
app.use(errorHandler);
app.listen(3000, '0.0.0.0');
