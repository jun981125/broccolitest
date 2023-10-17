package pack.order.controller;

import lombok.Data;

@Data
public class OrderBean {
	private int orderid ,productid;
	private String customerid, orderdate, quantity, ordertotalprice, orderstate, 
	pimage, model, brand, category;
	private int subtotal, ordercount;
}
