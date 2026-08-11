def diagnose(_conn, symptoms) do
  diagnosis_draft = ReqLLM.generate_text(model: model(), prompt: symptoms)
  Cases.save_draft_for_clinician_review(diagnosis_draft)
end
