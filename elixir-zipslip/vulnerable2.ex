def extract(archive, destination), do: :erl_tar.extract(archive, [:compressed, {:cwd, destination}])
