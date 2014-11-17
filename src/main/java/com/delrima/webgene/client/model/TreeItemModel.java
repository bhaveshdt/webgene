package com.delrima.webgene.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeItemModel<T> {

	private T selectedItem;
	private final Map<Integer, List<T>> idToItemMap = new HashMap<Integer, List<T>>();

	/**
	 * @return the selectedItem
	 */
	public T getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem
	 *            the selectedItem to set
	 */
	public void setSelectedItem(T selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * @return the idToItemMap
	 */
	public Map<Integer, List<T>> getIdToItemMap() {
		return idToItemMap;
	}

}
