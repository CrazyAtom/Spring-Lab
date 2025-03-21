package lab.bookshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.bookshop.domain.Item.Item;
import lab.bookshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);
	}

	@Transactional
	public void updateItem(Long itemId, String name, int price, int stockQuantity) {
		Item findItem = itemRepository.findOne(itemId);
		findItem.update(name, price, stockQuantity);
	}

	public List<Item> findItems() {
		return itemRepository.findAll();
	}

	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}
}
