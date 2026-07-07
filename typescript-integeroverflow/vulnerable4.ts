import { Controller, Get, Query } from '@nestjs/common';

@Controller('buffer')
export class BufferController {
  @Get()
  allocate(@Query('size') sizeStr: string, @Query('count') countStr: string): Buffer {
    const size = parseInt(sizeStr, 10) as number;
    const count = parseInt(countStr, 10) as number;
    const total = (size * count) | 0; // as number cast doesn't sanitize; | 0 truncates to int32
    return Buffer.alloc(total);
  }
}
