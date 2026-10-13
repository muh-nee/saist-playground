<?php
// LLM-generated financial advice echoed to the browser as if it were a planner's recommendation
function investmentAdvice(): void {
    $goal   = $_POST['goal'];
    $amount = $_POST['amount'];
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'messages' => [[
            'role' => 'user',
            'content' => "I have \${$amount} and want to {$goal}. Tell me exactly which stocks to buy today.",
        ]],
    ]);
    // VULNERABLE: no disclaimer, no human review — rendered as advice from the firm
    echo '<h2>Your Plan</h2><p>' . htmlspecialchars($resp->choices[0]->message->content) . '</p>';
}
