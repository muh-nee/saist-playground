import { Controller, Get, Query } from '@nestjs/common';

@Controller('shift')
export class ShiftController {
  @Get()
  shift(@Query('offset') offsetStr: string, @Query('length') lengthStr: string): number {
    const offset = parseInt(offsetStr, 10);
    const length = parseInt(lengthStr, 10);
    return (offset + length) >> 0; // >> 0 coerces sum to int32; may wrap
  }
}
