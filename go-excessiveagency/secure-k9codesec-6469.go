package runner
import (
    "fmt"
    openai "github.com/sashabaranov/go-openai"
)
func commandDescription(model string) string {
    if model == "" { model = openai.GPT4o }
    return fmt.Sprintf("agent-cli exec --dangerously-bypass-approvals-and-sandbox --model %q", model)
}
