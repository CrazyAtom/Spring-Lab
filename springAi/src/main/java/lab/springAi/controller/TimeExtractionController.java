package lab.springAi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lab.springAi.domain.TimeExtractionRequest;
import lab.springAi.domain.TimeExtractionResponse;
import lab.springAi.service.TimeExtractionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TimeExtractionController {

	private final TimeExtractionService timeExtractionService;

	@GetMapping("/")
	public TimeExtractionResponse extractTime(
		@RequestParam String content) {

		TimeExtractionRequest request = new TimeExtractionRequest(content);
		return timeExtractionService.extractTime(request);
	}
}
