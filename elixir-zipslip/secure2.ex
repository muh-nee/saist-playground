def extract(archive, destination) do
  entries = :erl_tar.table(archive, [:compressed])
  true = Enum.all?(entries, &contained_entry?(&1, destination))
  :erl_tar.extract(archive, [:compressed, {:cwd, destination}])
end
