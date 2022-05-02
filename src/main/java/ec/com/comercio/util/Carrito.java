package ec.com.comercio.util;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
	
	private List<Item> items;
	
	public Carrito() {
		this.items=new ArrayList<Item>();
	}

	public Carrito(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
