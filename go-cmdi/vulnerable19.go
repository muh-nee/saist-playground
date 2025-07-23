package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os/exec"
	
	"github.com/gorilla/websocket"
)

var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true // Allow connections from any origin
	},
}

type WSMessage struct {
	Type      string `json:"type"`
	Command   string `json:"command"`
	Target    string `json:"target"`
	Condition string `json:"condition"`
}

func main() {
	// Vulnerable WebSocket endpoint - command injection via WebSocket messages
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		conn, err := upgrader.Upgrade(w, r, nil)
		if err != nil {
			log.Printf("WebSocket upgrade failed: %v", err)
			return
		}
		defer conn.Close()
		
		fmt.Println("WebSocket client connected")
		
		for {
			var msg WSMessage
			err := conn.ReadJSON(&msg)
			if err != nil {
				log.Printf("WebSocket read error: %v", err)
				break
			}
			
			var response map[string]interface{}
			
			switch msg.Type {
			case "conditional_exec":
				// Vulnerable: using WebSocket message data in conditional commands
				if msg.Target == "" || msg.Command == "" {
					response = map[string]interface{}{
						"error": "Missing target or command in message",
					}
				} else {
					condition := msg.Condition
					if condition == "" {
						condition = "-f" // default file existence check
					}
					
					condCmd := fmt.Sprintf("test %s %s && %s %s || echo 'Condition failed'", 
						condition, msg.Target, msg.Command, msg.Target)
					
					cmd := exec.Command("sh", "-c", condCmd)
					output, err := cmd.Output()
					
					response = map[string]interface{}{
						"type":      msg.Type,
						"target":    msg.Target,
						"command":   msg.Command,
						"condition": condition,
						"full_command": condCmd,
						"result":    string(output),
					}
					
					if err != nil {
						response["error"] = fmt.Sprintf("Command failed: %v", err)
					}
				}
				
			case "batch_conditional":
				// Additional vulnerable pattern with batch conditional execution
				targets := []string{msg.Target}
				if msg.Target == "" {
					response = map[string]interface{}{
						"error": "Missing target for batch operation",
					}
				} else {
					results := make([]map[string]interface{}, 0)
					
					for i, target := range targets {
						batchCmd := fmt.Sprintf("test -e %s && echo 'Processing %s' && %s %s || echo 'Skipping %s'", 
							target, target, msg.Command, target, target)
						
						cmd := exec.Command("sh", "-c", batchCmd)
						output, err := cmd.Output()
						
						result := map[string]interface{}{
							"index":   i,
							"target":  target,
							"command": batchCmd,
							"result":  string(output),
						}
						
						if err != nil {
							result["error"] = fmt.Sprintf("Command failed: %v", err)
						}
						
						results = append(results, result)
					}
					
					response = map[string]interface{}{
						"type":    msg.Type,
						"command": msg.Command,
						"results": results,
					}
				}
				
			default:
				response = map[string]interface{}{
					"error": fmt.Sprintf("Unknown message type: %s", msg.Type),
				}
			}
			
			if err := conn.WriteJSON(response); err != nil {
				log.Printf("WebSocket write error: %v", err)
				break
			}
		}
		
		fmt.Println("WebSocket client disconnected")
	})
	
	// Serve a simple HTML page for WebSocket testing
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		html := `<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Command Injection Test</title>
</head>
<body>
    <h1>WebSocket Command Injection</h1>
    <div>
        <input type="text" id="target" placeholder="Target file/path" value="/etc/passwd">
        <input type="text" id="command" placeholder="Command" value="cat">
        <input type="text" id="condition" placeholder="Condition" value="-f">
        <button onclick="sendMessage()">Send Conditional Command</button>
    </div>
    <div>
        <h3>Response:</h3>
        <pre id="response"></pre>
    </div>
    
    <script>
        const ws = new WebSocket('ws://localhost:8080/ws');
        
        ws.onmessage = function(event) {
            document.getElementById('response').textContent = JSON.stringify(JSON.parse(event.data), null, 2);
        };
        
        function sendMessage() {
            const msg = {
                type: 'conditional_exec',
                target: document.getElementById('target').value,
                command: document.getElementById('command').value,
                condition: document.getElementById('condition').value
            };
            ws.send(JSON.stringify(msg));
        }
    </script>
</body>
</html>`
		w.Header().Set("Content-Type", "text/html")
		w.Write([]byte(html))
	})
	
	fmt.Println("Buffalo-style server starting on :8080")
	fmt.Println("Visit: http://localhost:8080 for WebSocket test page")
	fmt.Println("WebSocket endpoint: ws://localhost:8080/ws")
	log.Fatal(http.ListenAndServe(":8080", nil))
}