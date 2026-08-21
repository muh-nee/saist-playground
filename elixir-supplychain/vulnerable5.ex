defmodule SetupController do
  def setup_deps(feature) do
    {:ok, response} = ExOpenAI.Chat.create_chat_completion(
      [%{role: "user", content: "List Hex packages for: #{feature}. One per line."}],
      "gpt-4"
    )
    packages = response.choices |> hd() |> get_in([:message, :content]) |> String.split("\n")
    package_specs = Enum.map(packages, fn pkg ->
      {String.to_atom(String.trim(pkg)), "~> 1.0"}
    end)
    Mix.install(package_specs)
    {:ok, packages}
  end
end
