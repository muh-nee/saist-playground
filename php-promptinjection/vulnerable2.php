<?php
// Laravel request input piped into a Prism prompt as a single string
use Prism\Prism\Prism;
use Prism\Prism\Enums\Provider;
use Illuminate\Http\Request;

class ChatController
{
    public function handle(Request $request): string
    {
        $msg = $request->input('msg'); // tainted
        // VULNERABLE: no role separation, user string is the whole prompt body
        $response = Prism::text()
            ->using(Provider::OpenAI, 'gpt-4o-mini')
            ->withPrompt("Translate for our customer: {$msg}")
            ->asText();
        return $response->text;
    }
}
