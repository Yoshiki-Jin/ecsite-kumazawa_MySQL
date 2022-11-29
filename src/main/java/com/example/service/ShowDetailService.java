package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author inagakisaia
 *
 */
@Service
@Transactional
public class ShowDetailService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * IDから商品情報を検索します.
	 * 
	 * @param itemId ID
	 * @return 検索された商品情報
	 */
	public Item showDetail(int itemId) {
		Item item = itemRepository.load(itemId);
		List<Topping> toppingList = toppingRepository.findAll();
		item.setToppingList(toppingList);

		return item;
	}

}
