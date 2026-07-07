import { Controller, Get, Query } from '@nestjs/common';

@Controller('coerce')
export class CoerceController {
  @Get()
  coerce(@Query('value') valueStr: string): number {
    const value = parseInt(valueStr, 10);
    return value | 0; // | 0 coerces to int32; wraps if value > 2^31-1
  }
}
