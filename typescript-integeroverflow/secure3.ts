import { Controller, Get, Query, BadRequestException } from '@nestjs/common';

const MAX_BUFFER = 10 * 1024 * 1024;

@Controller('buffer')
export class SafeBufferController {
  @Get()
  allocate(@Query('size') sizeStr: string, @Query('count') countStr: string): Buffer {
    const size = parseInt(sizeStr, 10);
    const count = parseInt(countStr, 10);
    const total = size * count;
    if (total <= 0 || total > MAX_BUFFER) {
      throw new BadRequestException('invalid buffer size');
    }
    return Buffer.alloc(total); // bounds-checked; no bitwise coercion
  }
}
