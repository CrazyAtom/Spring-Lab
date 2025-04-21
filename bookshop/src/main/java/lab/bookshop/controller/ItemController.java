package lab.bookshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lab.bookshop.domain.Item.Book;
import lab.bookshop.domain.Item.Item;
import lab.bookshop.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new BookForm());
		return "items/createItemForm";
	}

	@PostMapping("/items/new")
	public String create(@Valid BookForm form, BindingResult result) {

		if (result.hasErrors()) {
			return "items/createItemForm";
		}

		Book book = Book.createBook(
			form.getName(),
			form.getPrice(),
			form.getStockQuantity(),
			form.getAuthor(),
			form.getIsbn());

		itemService.saveItem(book);
		return "redirect:/items";
	}

	@GetMapping("/items")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		return "items/itemList";
	}

	@GetMapping("/items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Book item = (Book)itemService.findOne(itemId);

		BookForm form = new BookForm();
		form.setId(itemId);
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setStockQuantity(item.getStockQuantity());
		form.setAuthor(item.getAuthor());
		form.setIsbn(item.getIsbn());

		model.addAttribute("form", form);
		return "items/updateItemForm";
	}

	@PostMapping("/items/{itemId}/edit")
	public String updateItemForm(@ModelAttribute("form") BookForm form) {
		itemService.updateItem(
			form.getId(),
			form.getName(),
			form.getPrice(),
			form.getStockQuantity());

		return "redirect:/items";
	}
}
