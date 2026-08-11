def extract(archive, destination), do: :zip.extract(archive, cwd: destination)
