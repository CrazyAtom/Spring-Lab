package lab.springAi.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import lab.springAi.domain.TimeExtractionRequest;
import lab.springAi.domain.TimeExtractionResponse;

class TimeExtractionServiceUnitTest {

	String API_KEY = "API_KEY";

	@Test
	public void extractTime() {
		// given
		OpenAiApi openAiApi = OpenAiApi.builder()
			.apiKey(API_KEY)
			.baseUrl("https://generativelanguage.googleapis.com/v1beta/openai/")
			.completionsPath("/chat/completions")
			.build();

		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
			.model("gemini-2.0-flash-lite")
			.temperature(0.0)
			.build();

		OpenAiChatModel chatModel = OpenAiChatModel.builder()
			.openAiApi(openAiApi)
			.defaultOptions(chatOptions)
			.build();

		TimeExtractionService service = new TimeExtractionService(chatModel);
		TimeExtractionRequest request = new TimeExtractionRequest("2025년 11월 28일 친구들과 밤 11시에 만나기");

		// when
		TimeExtractionResponse response = service.extractTime(request);

		// then
		assertThat(response.result()).isTrue();
		assertThat(response.hasTime()).isTrue();
		assertThat(response.datetime()).isEqualTo("2025-11-28T23:00:00");
		assertThat(response.content()).doesNotContain("11시");
	}
}
