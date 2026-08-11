def train(_input), do: Axon.Loop.run(model(), Datasets.verified_training_set!(), optimizer())
