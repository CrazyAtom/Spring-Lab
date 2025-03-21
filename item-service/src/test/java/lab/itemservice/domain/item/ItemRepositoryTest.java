package lab.itemservice.domain.item;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();

	@AfterEach
	void afterEach() {
		itemRepository.clearStore();
	}

	@Test
	public void save() throws Exception{
	    //given
		Item item = new Item("itemA", 10000, 10);

	    //when
		Item saveItem = itemRepository.save(item);

		//then
		itemRepository.findById(saveItem.getId());
		assertThat(saveItem).isEqualTo(item);
	}

	@Test
	public void findAll() throws Exception{
	    //given
		Item item1 = new Item("item1", 10000, 10);
		Item item2 = new Item("item2", 20000, 20);

		itemRepository.save(item1);
		itemRepository.save(item2);

		//when
		List<Item> result = itemRepository.findAll();

		//then
		assertThat(result.size()).isEqualTo(2);
		assertThat(result).contains(item1, item2);
	}

	@Test
	public void updateItem() throws Exception{
	    //given
		Item item1 = new Item("item1", 10000, 10);

		Item saveItem = itemRepository.save(item1);
		Long itemId = saveItem.getId();

		//when

		Item updateParam = new Item("item2", 20000, 20);
		itemRepository.update(itemId, updateParam);

	    //then
		Item findItem = itemRepository.findById(itemId);
		assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
		assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
		assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
	}
}
