import { Controller, Get, Query, BadRequestException } from '@nestjs/common';

@Controller('coerce')
export class SafeCoerceController {
  @Get()
  coerce(@Query('value') valueStr: string): number {
    const value = parseInt(valueStr, 10);
    if (value < 0 || value > Number.MAX_SAFE_INTEGER) {
      throw new BadRequestException('value out of range');
    }
    return value; // no bitwise coercion
  }
}
