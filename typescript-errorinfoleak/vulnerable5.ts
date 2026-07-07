import { Request, Response, NextFunction } from 'express';

export function globalErrorHandler(
  error: unknown,
  req: Request,
  res: Response,
  next: NextFunction
): void {
  res.status(500).json({ error: String(error) });
}
