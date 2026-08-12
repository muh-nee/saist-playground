import dev.langchain4j.model.openai.OpenAiChatModel;

public class TaskProcessor {
	private final OpenAiChatModel model;

	public TaskProcessor() {
		this.model = OpenAiChatModel.builder()
			.apiKey(System.getenv("OPENAI_API_KEY"))
			.modelName("gpt-4o-mini")
			.build();
	}

	public String processTask(String task) {
		String output = model.generate(task);
		String clean = output.replaceAll("\\[[0-9;]*m", "");
		System.out.println(clean);
		return clean;
	}
}
