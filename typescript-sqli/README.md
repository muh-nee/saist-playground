# typescript-sqli

Fixtures for the `datadog/typescript-sqli` rule. Each file targets a single TypeScript-flavored pattern so failures point at a specific gap.

## Vulnerable files (rule should fire)

### `vulnerable1.ts`
User input interpolated directly into a SQL template literal. The rule should flag tainted `userInput` flowing into `connection.query` without parameterization. This is the baseline — TS port of `javascript-sqli/vulnerable1.js`.

### `vulnerable2.ts`
`req.query.id` cast via `as string` (a TS type assertion, **not** a sanitizer) and concatenated into a SQL string. Tests that the rule doesn't lose taint through TS type assertions. Common false-negative trap.

### `vulnerable3.ts`
Typed class repository method using a template literal over fields from an `interface`. Tests that field access through typed objects still propagates taint.

### `vulnerable4.ts`
Decorator-based controller (`@Get`, `@Param`) with a template literal SQL. Tests that the rule recognizes parameter decorators (NestJS-style) as taint sources.

### `vulnerable5.ts`
`type` alias source + string concatenation. The `??` nullish-coalescing operator provides a default for `limit` and should **not** be misread as sanitization.

### `vulnerable6.tsx`
React component returning JSX with an async SQL call inside. Tests the TSX parser path and that mixed JSX + TS code doesn't break taint tracking.

## Secure files (rule should NOT fire)

### `secure1.ts`
Parameterized queries using positional `?` placeholders. Classic safe pattern.

### `secure2.ts`
Typed repository method passing user input via a parameter array. Tests that the rule correctly identifies parameterization through typed code paths.

### `secure3.ts`
Express handler using positional placeholders with the parameter array. Same source as `vulnerable2.ts` (`req.query.id as string`) but used safely — confirms the rule's distinction is sink-shape, not source-shape.

## Non-source files

### `types.d.ts`
Shim declarations only. Should be **filtered out** by the binary's `**/*.d.ts` ignore glob — confirm it never appears in scan logs.
