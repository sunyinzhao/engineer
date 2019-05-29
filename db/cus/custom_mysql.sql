SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS cus_equipment;
DROP TABLE IF EXISTS cus_custom;




/* Create Tables */

CREATE TABLE cus_custom
(
	-- 用户id
	id varchar(50) NOT NULL COMMENT '用户id',
	-- 用户姓名
	name varchar(20) NOT NULL COMMENT '用户姓名',
	-- 客户地址
	address varchar(100) COMMENT '客户地址',
	-- 电话
	phone varchar(15) COMMENT '电话',
	-- 职位
	position varchar(10) COMMENT '职位',
	-- 区域
	area varchar(30) COMMENT '区域',
	-- 房屋属性
	house_property varchar(10) COMMENT '房屋属性',
	-- 所属中心
	center varchar(10) COMMENT '所属中心',
	-- 所属部处
	buchu varchar(10) COMMENT '所属部处',
	-- 所属科组
	kezu varchar(10) COMMENT '所属科组',
	-- 台产权\上市\出租
	leixing varchar(10) COMMENT '台产权\上市\出租',
	-- 租户
	leaseholder varchar(20) COMMENT '租户',
	-- 租户电话
	leaseholder_phone varchar(15) COMMENT '租户电话',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);


CREATE TABLE cus_equipment
(
	id varchar(50) NOT NULL,
	-- 设备名称
	nam varchar(20) NOT NULL COMMENT '设备名称',
	-- 设备型号
	type varchar(20) COMMENT '设备型号',
	-- mac地址
	mac varchar(40) COMMENT 'mac地址',
	-- 设备编号
	number varchar(20) COMMENT '设备编号',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	-- 用户id
	cus_id varchar(50) NOT NULL COMMENT '用户id',
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE cus_equipment
	ADD FOREIGN KEY (cus_id)
	REFERENCES cus_custom (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



