import { Controller, Post, Body } from '@nestjs/common';
import { IsInt, Min, Max } from 'class-validator';

class MultiplyDto {
  @IsInt()
  @Min(0)
  @Max(100000)
  a!: number;

  @IsInt()
  @Min(0)
  @Max(100000)
  b!: number;
}

@Controller('multiply')
export class SafeMultiplyController {
  @Post()
  multiply(@Body() dto: MultiplyDto): number {
    return dto.a * dto.b; // class-validator enforces range; no int32 coercion
  }
}
