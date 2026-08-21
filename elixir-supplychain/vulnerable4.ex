defmodule DependencyController do
  def install(task) do
    {:ok, response} = ExOpenAI.Chat.create_chat_completion(
      [%{role: "user", content: "What Hex package should I use for: #{task}? Reply with only the package name."}],
      "gpt-4"
    )
    package_name = response.choices |> hd() |> get_in([:message, :content]) |> String.trim()
    package_atom = String.to_atom(package_name)
    Mix.install([{package_atom, "~> 1.0"}])
    {:ok, package_name}
  end
end
