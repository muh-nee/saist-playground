defmodule DependencyController do
  @approved_packages ~w[bumblebee axon nx ortex tokenizers]

  def install(task) do
    {:ok, response} = ExOpenAI.Chat.create_chat_completion(
      [%{role: "user", content: "What Hex package for: #{task}? Reply with only the package name."}],
      "gpt-4"
    )
    package_name = response.choices |> hd() |> get_in([:message, :content]) |> String.trim()
    if package_name in @approved_packages do
      package_atom = String.to_atom(package_name)
      Mix.install([{package_atom, "~> 1.0"}])
      {:ok, package_name}
    else
      {:error, :package_not_approved}
    end
  end
end
