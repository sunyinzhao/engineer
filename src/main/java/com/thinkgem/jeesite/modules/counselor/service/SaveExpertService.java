package com.thinkgem.jeesite.modules.counselor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.counselor.entity.PersonExpert;

@Service
@Transactional(readOnly = false)
@Lazy(false)
public class SaveExpertService {

	@Autowired
	private PersonExpertService personExpertService;
	
	//11.16 需要把所有的expert 复制出来一份, 把examinetype 为3 改成4
    public void saveExpert(String recordId){
        //防止重复创建,如果表中有数据了,就不再往下走
        PersonExpert p1 = new PersonExpert();
        p1.setExamineType("4");
        p1.setRecordId(recordId);
        List<PersonExpert> p1List = personExpertService.findList(p1);
        if(p1List!=null&&p1List.size()>0){
            return;
        }
        PersonExpert personExpert = new PersonExpert();
        personExpert.setExamineType("3");
        personExpert.setRecordId(recordId);
        List<PersonExpert> list = personExpertService.findList(personExpert);
        if(list!=null&&list.size()>0){
            for(PersonExpert p : list){
                p.setId(null);
                p.setExamineType("4");
                personExpertService.save(p);
            }
        }
    }
}
