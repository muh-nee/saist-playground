defmodule AppWeb.TokenizerController do
  use AppWeb, :controller

  @tokenizer_repo "approved-org/distilbert-base-uncased"

  def load_tokenizer(conn, _params) do
    {:ok, tokenizer} = Bumblebee.load_tokenizer({:hf, @tokenizer_repo})
    json(conn, %{status: "loaded"})
  end
end
