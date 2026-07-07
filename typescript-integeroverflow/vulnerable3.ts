import { Controller, Post, Body } from '@nestjs/common';

interface MultiplyRequest {
  a: number;
  b: number;
}

@Controller('multiply')
export class MultiplyController {
  @Post()
  multiply(@Body() req: MultiplyRequest): number {
    return Math.imul(req.a, req.b); // 32-bit integer multiplication; wraps silently
  }
}
