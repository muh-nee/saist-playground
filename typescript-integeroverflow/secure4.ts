// Standard arithmetic on TypeScript/JS numbers does not overflow (64-bit float)
import { Controller, Get, Query } from '@nestjs/common';

@Controller('add')
export class AddController {
  @Get()
  add(@Query('a') aStr: string, @Query('b') bStr: string): number {
    const a = parseFloat(aStr);
    const b = parseFloat(bStr);
    return a + b; // 64-bit float addition; no int32 wraparound
  }
}
