#更新工程师公司
update enterprise_workers w, sys_user u set w.pid = u.id where u.name = w.company_name and u.user_type = '7'

#咨詢工程师
alter table enterprise_workers add company_name varchar(200);
alter table enterprise_workers add review_company varchar(200);
alter table enterprise_workers add z_specialty varchar(200);
alter table enterprise_workers add f_specialty varchar(200);
alter table enterprise_workers add company_contact varchar(200);
alter table enterprise_workers add company_area varchar(200);
alter table enterprise_workers add company_tel varchar(200);
-- 增加驳回原因
ALTER TABLE `sys_examine`ADD COLUMN `reject_type`  varchar(50) NULL COMMENT '驳回原因(dictType)' AFTER `result_type`;



