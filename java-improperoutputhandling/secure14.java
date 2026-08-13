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
		String clean = output.replaceAll("\u001B(?:\\[[0-9;]*[A-Za-z]|\\][^\u0007\u001B]*(?:\u0007|\u001B\\\\))", "");
		System.out.println(clean);
		return clean;
	}
}
