import { Controller, Get, Query } from '@nestjs/common';

@Controller('bits')
export class BitsController {
  @Get()
  bits(@Query('flags') flagsStr: string, @Query('mask') maskStr: string): number {
    const flags = parseInt(flagsStr, 10);
    const mask = parseInt(maskStr, 10);
    return flags & mask; // bitwise AND coerces both to int32
  }
}
