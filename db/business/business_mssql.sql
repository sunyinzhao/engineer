
/* Drop Tables */

IF ObJECt_ID('[bs_business]') IS NOT NULL DROP TABLE [bs_business];
IF ObJECt_ID('[bs_car]') IS NOT NULL DROP TABLE [bs_car];
IF ObJECt_ID('[bs_consultation]') IS NOT NULL DROP TABLE [bs_consultation];
IF ObJECt_ID('[bs_engineer]') IS NOT NULL DROP TABLE [bs_engineer];
IF ObJECt_ID('[bs_maintain_content]') IS NOT NULL DROP TABLE [bs_maintain_content];
IF ObJECt_ID('[bs_receipt]') IS NOT NULL DROP TABLE [bs_receipt];
IF ObJECt_ID('[bs_service]') IS NOT NULL DROP TABLE [bs_service];




/* Create Tables */

CREATE TABLE [bs_business]
(
	-- 用户id
	[id] varchar(50) NOT NULL,
	-- 联系人
	[name] varchar(20) NOT NULL,
	-- 联系电话
	[phone] varchar(15) NOT NULL,
	-- 客户Id
	[cus_id] varchar(64) NOT NULL,
	-- 报修类型
	[repair_type] varchar(64) NOT NULL,
	-- 报修故障
	[repair_failure] varchar(64) NOT NULL,
	-- 客户问题
	[cus_problem] varchar(300),
	-- 答复内容
	[serv_answer] varchar(300),
	-- 业务单类型
	[bus_type] varchar(64),
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


CREATE TABLE [bs_car]
(
	[id] varchar(64),
	[car_num] varbinary(64),
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
	[del_flag] char(1) DEFAULT '0' NOT NULL
);


CREATE TABLE [bs_consultation]
(
	-- 用户id
	[id] varchar(50) NOT NULL,
	-- 联系人
	[name] varchar(20) NOT NULL,
	-- 联系电话
	[phone] varchar(15) NOT NULL,
	[number] varchar(20),
	-- 报修类型
	[repair_type] varchar(64) NOT NULL,
	-- 报修故障
	[repair_failure] varchar(64) NOT NULL,
	-- 客户问题
	[cus_problem] varchar(300),
	-- 答复内容
	[serv_answer] varchar(300),
	-- 客户Id
	[cus_id] varchar(64) NOT NULL,
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


CREATE TABLE [bs_engineer]
(
	[id] varchar(64) NOT NULL,
	[engineer_id] varchar(64) NOT NULL,
	[receipt_id] varchar(64) NOT NULL,
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


CREATE TABLE [bs_maintain_content]
(
	[id] varchar(64) NOT NULL,
	[part_name] varchar(64) NOT NULL,
	[handler_type] varchar(64) NOT NULL,
	[price] decimal(10,2),
	[counts] int,
	[total] decimal(10,2),
	[receipt_id] varchar(64) NOT NULL,
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


CREATE TABLE [bs_receipt]
(
	[id] varchar(64) NOT NULL,
	[repair_data] datetime NOT NULL,
	[repair_explain] varchar(100),
	[again_document] char(1),
	[t_tx] varchar(50),
	[t_rx] varchar(50),
	[t_snr] varchar(50),
	[t_downstream] varchar(50),
	[t_terminal_a] varchar(50),
	[t_terminal_b] varchar(50),
	[t_terminal_c] varchar(50),
	[t_terminal_d] varchar(50),
	[server_id] varchar(64),
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


CREATE TABLE [bs_service]
(
	-- 用户id
	[id] varchar(50) NOT NULL,
	-- 联系人
	[name] varchar(20) NOT NULL,
	-- 联系电话
	[phone] varchar(15) NOT NULL,
	-- 客户Id
	[cus_id] varchar(64) NOT NULL,
	[number] varchar(64),
	-- 报修类型
	[repair_type] varchar(64) NOT NULL,
	-- 报修故障
	[repair_failure] varchar(64) NOT NULL,
	-- 客户问题
	[cus_problem] varchar(300),
	-- 答复内容
	[serv_answer] varchar(300),
	-- 业务单类型
	[bus_type] varchar(64),
	-- 服务类型
	[server_type] varchar(64),
	-- 预约时间
	[reserve_date] datetime,
	-- 车辆信息
	[car] varchar(64),
	-- 司机
	[driver] varchar(64),
	[task_user] varchar(64),
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
	-- 状态
	[status] varchar(1),
	PRIMARY KEY ([id])
);



