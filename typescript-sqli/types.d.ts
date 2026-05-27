declare const connection: {
	query: (sql: string, params?: unknown[], cb?: (err: any, rows: any) => void) => Promise<unknown[]>;
};

declare function Get(path: string): MethodDecorator;
declare function Param(name: string): ParameterDecorator;
