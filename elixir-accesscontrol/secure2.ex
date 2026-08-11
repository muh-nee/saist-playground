def delete(conn, %{"id" => id}) do
  document = Repo.get!(Document, id)
  :ok = Bodyguard.permit!(Documents, :delete, conn.assigns.current_user, document)
  Repo.delete!(document)
end
