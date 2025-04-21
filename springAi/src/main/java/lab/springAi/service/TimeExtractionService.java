package lab.springAi.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lab.springAi.domain.TimeExtractionRequest;
import lab.springAi.domain.TimeExtractionResponse;

@Service
public class TimeExtractionService {

	private final ChatClient chatClient;

	public TimeExtractionService(ChatModel chatModel) {
		this.chatClient = ChatClient.builder(chatModel).build();
	}

	private final ObjectMapper objectMapper = new ObjectMapper();

	public TimeExtractionResponse extractTime(TimeExtractionRequest request) {
		String prompt = generatePrompt(request);
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();

		if (chatResponse == null) {
			return new TimeExtractionResponse(false, false, null, null);
			// return new RuntimeException("Chat response is null");
		}

		Generation result = chatResponse.getResult();

		AssistantMessage output = result.getOutput();
		String text = output.getText();

		return parseResult(text);
	}

	private String generatePrompt(TimeExtractionRequest request) {
		return String.format("""
			 작업: 텍스트에서 시간을 추출하여 JSON 형식으로 반환합니다.
			
			 - 텍스트에는 날짜, 시간 또는 둘 다 포함될 수 있습니다.
			 - 시간을 식별하여 ISO 8601 (YYYY-MM-DDTHH:MM:SS)로 변환 합니다.
			 - 텍스트에서 날짜, 시간을 제거 합니다.
			 - 텍스트에서 식별된 날짜, 시간을 제거합니다. 나머지 텍스트는 `content`가 됩니다.
			 - 만약, 시간이 없으면 다음을 반환합니다:
			   { "result": true, "hasTime": false}
			 - 시간이 여러개 있으면 다음을 반환 합니다:
			   { "result": false }
			
			 다음 필드와 함께 반드시 JSON 형식으로만 응답해야 합니다.
			 - result
			 - hasTime
			 - datetime
			 - content
			
			 No explanations.
			
			 ===
			
			 input:			
			 {
			     "content": "%s"
			 }
			""", request.content());
	}

	private TimeExtractionResponse parseResult(String text) {
		String jsonText = text.lines()
			.filter(line -> !line.startsWith("```"))
			.reduce("", (a, b) -> a + b);
		try {
			return objectMapper.readValue(jsonText, TimeExtractionResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
