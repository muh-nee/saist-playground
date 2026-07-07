import { Catch, ExceptionFilter, ArgumentsHost, Logger } from '@nestjs/common';

@Catch()
export class SafeAllExceptionsFilter implements ExceptionFilter {
  private readonly logger = new Logger(SafeAllExceptionsFilter.name);

  catch(exception: unknown, host: ArgumentsHost): void {
    this.logger.error('Unhandled exception', exception);
    host.switchToHttp().getResponse().status(500).json({
      message: 'internal server error',
    });
  }
}
