DROP VIEW IF EXISTS t_shoping_member;
DROP TABLE IF EXISTS t_goods_detail_image;
DROP TABLE IF EXISTS t_shopping_order;
DROP TABLE IF EXISTS t_shopping_cart;
DROP TABLE IF EXISTS t_shopping_member;
DROP TABLE IF EXISTS t_shopping_goods;
DROP SEQUENCE IF EXISTS seq_goods_id;
DROP SEQUENCE IF EXISTS seq_image_id;
DROP SEQUENCE IF EXISTS seq_order_id;
DROP SEQUENCE IF EXISTS order_seq_num;

CREATE TABLE t_shopping_goods (
    goods_id INTEGER PRIMARY KEY,
    goods_sort VARCHAR(50),
    goods_title VARCHAR(200),
    goods_writer VARCHAR(100),
    goods_publisher VARCHAR(100),
    goods_price INTEGER,
    goods_sales_price INTEGER,
    goods_point INTEGER,
    goods_published_date DATE,
    goods_total_page INTEGER,
    goods_isbn VARCHAR(50),
    goods_delivery_price VARCHAR(20),
    goods_delivery_date DATE,
    goods_status VARCHAR(20),
    goods_writer_intro CLOB,
    goods_contents_order CLOB,
    goods_intro CLOB,
    goods_publisher_comment CLOB,
    goods_recommendation CLOB,
    goods_creDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE t_goods_detail_image (
    image_id INTEGER PRIMARY KEY,
    goods_id INTEGER NOT NULL,
    fileName VARCHAR(200) NOT NULL,
    fileType VARCHAR(50),
    reg_id VARCHAR(50),
    CONSTRAINT fk_goods_image FOREIGN KEY (goods_id) REFERENCES t_shopping_goods(goods_id)
);

CREATE TABLE t_shopping_member (
    member_id VARCHAR(50) PRIMARY KEY,
    member_pw VARCHAR(200),
    member_name VARCHAR(100),
    member_gender VARCHAR(10),
    member_birth_y VARCHAR(4),
    member_birth_m VARCHAR(2),
    member_birth_d VARCHAR(2),
    member_birth_gn VARCHAR(2),
    tel1 VARCHAR(4),
    tel2 VARCHAR(4),
    tel3 VARCHAR(4),
    hp1 VARCHAR(4),
    hp2 VARCHAR(4),
    hp3 VARCHAR(4),
    smssts_yn VARCHAR(1),
    email1 VARCHAR(100),
    email2 VARCHAR(100),
    emailsts_yn VARCHAR(1),
    zipcode VARCHAR(20),
    roadAddress VARCHAR(200),
    jibunAddress VARCHAR(200),
    namujiAddress VARCHAR(200),
    joinDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    del_yn VARCHAR(1)
);

CREATE VIEW t_shoping_member AS SELECT * FROM t_shopping_member;

CREATE TABLE t_shopping_cart (
    cart_id INTEGER PRIMARY KEY,
    goods_id INTEGER NOT NULL,
    member_id VARCHAR(50) NOT NULL,
    cart_goods_qty INTEGER DEFAULT 1,
    creDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_goods FOREIGN KEY (goods_id) REFERENCES t_shopping_goods(goods_id),
    CONSTRAINT fk_cart_member FOREIGN KEY (member_id) REFERENCES t_shopping_member(member_id)
);

CREATE TABLE t_shopping_order (
    order_seq_num INTEGER PRIMARY KEY,
    order_id INTEGER,
    orderer_id VARCHAR(50),
    member_id VARCHAR(50),
    goods_id INTEGER,
    goods_title VARCHAR(200),
    goods_sales_price INTEGER,
    order_total_price INTEGER,
    order_goods_qty INTEGER,
    orderer_name VARCHAR(100),
    receiver_name VARCHAR(100),
    receiver_hp1 VARCHAR(4),
    receiver_hp2 VARCHAR(4),
    receiver_hp3 VARCHAR(4),
    receiver_tel1 VARCHAR(4),
    receiver_tel2 VARCHAR(4),
    receiver_tel3 VARCHAR(4),
    delivery_address VARCHAR(500),
    delivery_message VARCHAR(500),
    delivery_method VARCHAR(50),
    gift_wrapping VARCHAR(50),
    pay_method VARCHAR(50),
    card_com_name VARCHAR(100),
    card_pay_month VARCHAR(20),
    orderer_hp VARCHAR(30),
    pay_orderer_hp_num VARCHAR(30),
    pay_order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_state VARCHAR(50),
    final_total_price VARCHAR(50),
    goods_qty INTEGER,
    goods_fileName VARCHAR(200)
);

CREATE SEQUENCE seq_goods_id START WITH 2000 INCREMENT BY 1;
CREATE SEQUENCE seq_image_id START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE seq_order_id START WITH 4001 INCREMENT BY 1;
CREATE SEQUENCE order_seq_num START WITH 5000 INCREMENT BY 1;
