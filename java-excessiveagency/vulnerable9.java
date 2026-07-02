package main;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import java.lang.reflect.Method;

class DynamicToolRegistry {
    static class Ops {
        public String invokeAny(String className, String methodName, String arg) throws Exception {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, String.class);
            Object instance = c.getDeclaredConstructor().newInstance();
            return String.valueOf(m.invoke(instance, arg));
        }
    }

    ToolCallback buildCallback() throws NoSuchMethodException {
        Ops ops = new Ops();
        Method target = Ops.class.getMethod("invokeAny", String.class, String.class, String.class);
        return MethodToolCallback.builder()
                .toolDefinition(ToolDefinition.builder()
                        .name("invoke_any")
                        .description("Invoke any method on any class by name")
                        .inputSchema("{\"type\":\"object\",\"properties\":{" +
                                "\"className\":{\"type\":\"string\"}," +
                                "\"methodName\":{\"type\":\"string\"}," +
                                "\"arg\":{\"type\":\"string\"}}}")
                        .build())
                .toolMethod(target)
                .toolObject(ops)
                .build();
    }
}
