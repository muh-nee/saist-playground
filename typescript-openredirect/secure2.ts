import { Controller, Get, Query, Redirect } from '@nestjs/common';

// Safe: NestJS @Redirect with hardcoded URL (no user input)
@Controller('auth')
export class SafeAuthController {
  @Get('logout')
  @Redirect('/login', 302)
  logout() {
    // Clean up session — redirect destination is hardcoded, not user-controlled
  }

  // Safe: validate returnUrl before using it
  @Get('callback')
  callback(@Query('returnUrl') returnUrl: string) {
    const ALLOWED = new Set<string>(['/dashboard', '/profile']);
    const safeUrl: string = ALLOWED.has(returnUrl) ? returnUrl : '/dashboard';
    return { url: safeUrl, statusCode: 302 };
  }
}
