defmodule AppWeb.ModelController do
  use AppWeb, :controller

  def load_model(conn, %{"repo" => repository}) do
    {:ok, model_info} = Bumblebee.load_model({:hf, repository})
    json(conn, %{status: "loaded"})
  end
end
