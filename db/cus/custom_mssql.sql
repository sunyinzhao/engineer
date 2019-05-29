
/* Drop Tables */

IF ObJECt_ID('[cus_equipment]') IS NOT NULL DROP TABLE [cus_equipment];
IF ObJECt_ID('[cus_custom]') IS NOT NULL DROP TABLE [cus_custom];




/* Create Tables */

CREATE TABLE [cus_custom]
(
	-- 用户id
	[id] varchar(50) NOT NULL,
	-- 用户姓名
	[name] varchar(20) NOT NULL,
	-- 客户地址
	[address] varchar(100),
	-- 电话
	[phone] varchar(15),
	-- 职位
	[position] varchar(10),
	-- 区域
	[area] varchar(30),
	-- 房屋属性
	[house_property] varchar(10),
	-- 所属中心
	[center] varchar(10),
	-- 所属部处
	[buchu] varchar(10),
	-- 所属科组
	[kezu] varchar(10),
	-- 台产权\上市\出租
	[leixing] varchar(10),
	-- 租户
	[leaseholder] varchar(20),
	-- 租户电话
	[leaseholder_phone] varchar(15),
	-- 创建者
	[create_by] varchar(64) NOT NULL,
	-- 创建时间
	[create_date] datetime NOT NULL,
	-- 更新者
	[update_by] varchar(64) NOT NULL,
	-- 更新时间
	[update_date] datetime NOT NULL,
	-- 备注信息
	[remarks] nvarchar(255),
	-- 删除标记（0：正常；1：删除）
	[del_flag] char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY ([id])
);


CREATE TABLE [cus_equipment]
(
	[id] varchar(50) NOT NULL,
	-- 设备名称
	[nam] varchar(20) NOT NULL,
	-- 设备型号
	[type] varchar(20),
	-- mac地址
	[mac] varchar(40),
	-- 设备编号
	[number] varchar(20),
	-- 创建者
	[create_by] varchar(64) NOT NULL,
	-- 创建时间
	[create_date] datetime NOT NULL,
	-- 更新者
	[update_by] varchar(64) NOT NULL,
	-- 更新时间
	[update_date] datetime NOT NULL,
	-- 备注信息
	[remarks] nvarchar(255),
	-- 删除标记（0：正常；1：删除）
	[del_flag] char(1) DEFAULT '0' NOT NULL,
	-- 用户id
	[cus_id] varchar(50) NOT NULL,
	PRIMARY KEY ([id])
);



/* Create Foreign Keys */

ALTER TABLE [cus_equipment]
	ADD FOREIGN KEY ([cus_id])
	REFERENCES [cus_custom] ([id])
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



