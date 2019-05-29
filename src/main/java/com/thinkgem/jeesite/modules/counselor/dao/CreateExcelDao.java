package com.thinkgem.jeesite.modules.counselor.dao;


import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.Excel4Record;

import java.util.List;

@MyBatisDao
public interface CreateExcelDao {

    List<Excel4Record> excel4Record();
}
