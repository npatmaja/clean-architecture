INSERT INTO public.orders (order_number,user_id,shipping_address) VALUES 
('3dd143ef-5ddf-4808-995c-c68a544911d8','1x1','JL Mega Kuningan'),
('aeb1792e-4537-4a54-a1d4-9d6e9c09547a','1x2','JL Mega Emasan'),
('79a8ffa7-0d59-4c3d-8f15-4427dd9cedba', '1x1', 'JL Mega Kuningan');

INSERT INTO public.order_items (order_number,product_code,quantity,price) VALUES
('3dd143ef-5ddf-4808-995c-c68a544911d8','CODE_A',1,13000),
('3dd143ef-5ddf-4808-995c-c68a544911d8','CODE_B',2,12000),
('aeb1792e-4537-4a54-a1d4-9d6e9c09547a','CODE_C',1,20000),
('79a8ffa7-0d59-4c3d-8f15-4427dd9cedba','CODE_D',1,40000);

