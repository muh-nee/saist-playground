import { Controller, Get, Query, Redirect } from '@nestjs/common';

// Vulnerable: NestJS @Redirect decorator returning user-supplied URL
@Controller('auth')
export class AuthController {
  @Get('callback')
  @Redirect()
  callback(@Query('returnUrl') returnUrl: string) {
    // VULNERABLE: user-controlled URL returned as redirect destination
    // NestJS will issue a redirect to whatever returnUrl is
    return { url: returnUrl, statusCode: 302 };
  }
}
