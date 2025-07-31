import java.io.*;
import java.nio.file.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;

public class secure5 {
    private static final String ALLOWED_BASE_PATH = "/tmp/safe";
    
    public void compressFile(String filename, int level) throws IOException {
        if (filename == null || level < 1 || level > 9) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        
        Path filePath = Paths.get(ALLOWED_BASE_PATH, filename).normalize();
        if (!filePath.startsWith(ALLOWED_BASE_PATH)) {
            throw new SecurityException("Path traversal attempt detected");
        }
        
        String[] command = {"gzip", "-" + level, filePath.toString()};
        Process process = Runtime.getRuntime().exec(command);
    }
    
    static class CompressHandler implements HttpHandler {
        private secure5 compressor = new secure5();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String query = new String(exchange.getRequestBody().readAllBytes());
                Map<String, String> params = parseQuery(query);
                
                String filepath = params.get("filepath");
                String levelStr = params.get("level");
                
                try {
                    int level = Integer.parseInt(levelStr);
                    compressor.compressFile(filepath, level);
                    
                    String response = "File compressed successfully";
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (Exception e) {
                    String response = "Error: " + e.getMessage();
                    exchange.sendResponseHeaders(400, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.getResponseBody().close();
            }
        }
        
        private Map<String, String> parseQuery(String query) {
            Map<String, String> result = new HashMap<>();
            if (query != null && !query.isEmpty()) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        try {
                            result.put(URLDecoder.decode(keyValue[0], "UTF-8"), 
                                     URLDecoder.decode(keyValue[1], "UTF-8"));
                        } catch (Exception e) {
                            // Skip malformed pairs
                        }
                    }
                }
            }
            return result;
        }
    }
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/compress", new CompressHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
        System.out.println("Send POST requests to /compress with filepath and level parameters");
    }
}