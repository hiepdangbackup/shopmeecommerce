package com.shopme.common.entity;

public class Main {

	public static void main(String[] args) {
		OrderStatus newStatus = OrderStatus.NEW;
		System.out.println(newStatus.defaultDescription());
	}
	
}
