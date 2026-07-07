import { Request, Response, NextFunction } from 'express';

export function safeErrorHandler(
  error: Error,
  req: Request,
  res: Response,
  next: NextFunction
): void {
  console.error(error);
  res.status(500).json({ error: 'internal server error' });
}
