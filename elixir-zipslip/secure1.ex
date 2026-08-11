def extract(archive, destination) do
  :ok = validate_archive_entries!(archive, destination)
  :zip.extract(archive, cwd: destination)
end
