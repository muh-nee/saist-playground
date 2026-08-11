def recommend(conn, profile), do: json(conn, %{financial_recommendation: LangChain.run(chain(), profile).content})
