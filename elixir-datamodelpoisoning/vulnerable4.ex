defmodule AppWeb.ModelController do
  use AppWeb, :controller

  def load_tokenizer(conn, %{"tokenizer" => tokenizer_name}) do
    {:ok, tokenizer} = Bumblebee.load_tokenizer({:hf, tokenizer_name})
    json(conn, %{status: "loaded"})
  end
end
