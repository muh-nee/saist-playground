import { Controller, Get, Req, Logger } from '@nestjs/common';
import { Request } from 'express';

@Controller('profile')
export class ProfileController {
  private readonly logger = new Logger(ProfileController.name);

  @Get()
  getProfile(@Req() req: Request): { status: string } {
    // VULNERABLE: Authorization header interpolated directly into log message template literal
    this.logger.log(`JWT: ${req.headers.authorization}`);
    return { status: 'ok' };
  }
}
