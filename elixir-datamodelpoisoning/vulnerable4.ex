defmodule AppWeb.ModelController do
  use AppWeb, :controller

  def load_onnx(conn, %{"model_path" => path}) do
    {:ok, model} = Ortex.load(path)
    json(conn, %{status: "loaded"})
  end
end
