defmodule Endpoint do
  use Plug.Session, store: :cookie, key: "_session", signing_salt: "salt"
end
