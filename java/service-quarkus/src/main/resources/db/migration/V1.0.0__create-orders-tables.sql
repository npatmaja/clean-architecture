CREATE TABLE public.orders (
	order_number uuid NOT NULL,
	user_id varchar(100) NULL,
	shipping_address varchar(1000) NULL,
	CONSTRAINT orders_pk PRIMARY KEY (order_number)
);

CREATE TABLE public.order_items (
	item_id bigserial NOT NULL,
	order_number uuid NOT NULL,
	product_code varchar(100) NOT NULL,
	quantity int4 NULL,
	price numeric(19,2) NULL,
	CONSTRAINT order_items_pk PRIMARY KEY (item_id),
	CONSTRAINT order_items_fk FOREIGN KEY (order_number) REFERENCES orders(order_number)
);

