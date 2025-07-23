package main

import (
	"fmt"
	"net/http"
	"os/exec"
	
	"github.com/beego/beego/v2/server/web"
)

type MainController struct {
	web.Controller
}

// Vulnerable endpoint - command injection via HTTP header
func (c *MainController) ExecuteCommand() {
	// Get command from custom header
	command := c.Ctx.Input.Header("X-Command")
	if command == "" {
		c.Data["json"] = map[string]string{"error": "Missing X-Command header"}
		c.Ctx.Output.SetStatus(400)
		c.ServeJSON()
		return
	}
	
	// Get additional parameters from other headers
	target := c.Ctx.Input.Header("X-Target")
	options := c.Ctx.Input.Header("X-Options")
	
	if target == "" {
		target = "/tmp"
	}
	
	// Vulnerable: using header values directly in command
	var compoundCmd string
	if options != "" {
		compoundCmd = fmt.Sprintf("echo 'Starting %s' && %s %s %s && echo 'Done'", 
			command, command, options, target)
	} else {
		compoundCmd = fmt.Sprintf("echo 'Starting %s' && %s %s && echo 'Done'", 
			command, command, target)
	}
	
	cmd := exec.Command("bash", "-c", compoundCmd)
	output, err := cmd.Output()
	
	response := map[string]interface{}{
		"command": command,
		"target":  target,
		"options": options,
		"full_command": compoundCmd,
		"result": string(output),
	}
	
	if err != nil {
		response["error"] = fmt.Sprintf("Command failed: %v", err)
		c.Ctx.Output.SetStatus(500)
	}
	
	c.Data["json"] = response
	c.ServeJSON()
}

// Additional vulnerable endpoint for system monitoring
func (c *MainController) Monitor() {
	// Get monitoring parameters from headers
	service := c.Ctx.Input.Header("X-Service")
	action := c.Ctx.Input.Header("X-Action")
	
	if service == "" {
		c.Data["json"] = map[string]string{"error": "Missing X-Service header"}
		c.Ctx.Output.SetStatus(400)
		c.ServeJSON()
		return
	}
	
	if action == "" {
		action = "status"
	}
	
	// Vulnerable: constructing system commands with header values
	var monitorCmd string
	switch action {
	case "start":
		monitorCmd = fmt.Sprintf("systemctl start %s && systemctl status %s", service, service)
	case "stop":
		monitorCmd = fmt.Sprintf("systemctl stop %s && systemctl status %s", service, service)
	case "restart":
		monitorCmd = fmt.Sprintf("systemctl restart %s && systemctl status %s", service, service)
	default:
		monitorCmd = fmt.Sprintf("systemctl status %s", service)
	}
	
	cmd := exec.Command("bash", "-c", monitorCmd)
	output, err := cmd.Output()
	
	response := map[string]interface{}{
		"service": service,
		"action":  action,
		"command": monitorCmd,
		"result":  string(output),
	}
	
	if err != nil {
		response["error"] = fmt.Sprintf("Command failed: %v", err)
		c.Ctx.Output.SetStatus(500)
	}
	
	c.Data["json"] = response
	c.ServeJSON()
}

func main() {
	web.Router("/execute", &MainController{}, "post:ExecuteCommand")
	web.Router("/monitor", &MainController{}, "post:Monitor")
	
	fmt.Println("Beego server starting on :8080")
	fmt.Println("Try: curl -X POST -H 'X-Command: ls' -H 'X-Target: /etc' http://localhost:8080/execute")
	fmt.Println("Try: curl -X POST -H 'X-Service: nginx' -H 'X-Action: status' http://localhost:8080/monitor")
	
	web.Run(":8080")
}