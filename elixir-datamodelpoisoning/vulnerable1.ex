def train(user_uploaded_dataset), do: Axon.Loop.run(model(), user_uploaded_dataset, optimizer())
