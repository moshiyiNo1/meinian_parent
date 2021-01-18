package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = TravelItemService.class)//发布成一个服务，使用dubbo包内的service
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired//service依赖dao
    TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //分页插件，等同SQL中limit()
        PageHelper.startPage(currentPage,pageSize);
        Page<TravelItem> page = travelItemDao.findPage(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        //因跟团游中包含多个自由行，需先查找是否有跟团游中包含该自由行，后续进行删除操作
        travelItemDao.delete(id);
    }

    @Override
    public TravelItem getTravelItemById(Integer id) {
        return travelItemDao.getTravelItemById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }
}
