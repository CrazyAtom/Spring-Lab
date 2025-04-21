package lab.springAi.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lab.springAi.domain.TimeExtractionRequest;
import lab.springAi.domain.TimeExtractionResponse;

@SpringBootTest
@ActiveProfiles("test")
public class TimeExtractionServiceIntegrationTest {

	@Autowired
	private TimeExtractionService timeExtractionService;

	@Test
	public void extractTime() {
		// given
		TimeExtractionRequest request = new TimeExtractionRequest("2025년 11월 28일 친구들과 밤 11시에 만나기");

		// when
		TimeExtractionResponse response = timeExtractionService.extractTime(request);

		// then
		assertThat(response.result()).isTrue();
		assertThat(response.hasTime()).isTrue();
		assertThat(response.datetime()).isEqualTo("2025-11-28T23:00:00");
		assertThat(response.content()).doesNotContain("11시");
	}
}
