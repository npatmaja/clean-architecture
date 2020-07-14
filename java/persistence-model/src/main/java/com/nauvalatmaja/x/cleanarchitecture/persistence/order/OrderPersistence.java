package com.nauvalatmaja.x.cleanarchitecture.persistence.order;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPersistence {
	@Id
	@Column(name = "order_number")
	private UUID orderNumber;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "shipping_address")
	private String shippingAddress;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderItemPersistence> items;
}
