/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.iu.service.impl;

import me.zhengjie.iu.domain.IuFriend;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.iu.repository.IuFriendRepository;
import me.zhengjie.iu.service.IuFriendService;
import me.zhengjie.iu.service.dto.IuFriendDto;
import me.zhengjie.iu.service.dto.IuFriendQueryCriteria;
import me.zhengjie.iu.service.mapstruct.IuFriendMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Ronald Chan
* @date 2022-02-27
**/
@Service
@RequiredArgsConstructor
public class IuFriendServiceImpl implements IuFriendService {

    private final IuFriendRepository iuFriendRepository;
    private final IuFriendMapper iuFriendMapper;

    @Override
    public Map<String,Object> queryAll(IuFriendQueryCriteria criteria, Pageable pageable){
        Page<IuFriend> page = iuFriendRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(iuFriendMapper::toDto));
    }

    @Override
    public List<IuFriendDto> queryAll(IuFriendQueryCriteria criteria){
        return iuFriendMapper.toDto(iuFriendRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public IuFriendDto findById(Long id) {
        IuFriend iuFriend = iuFriendRepository.findById(id).orElseGet(IuFriend::new);
        ValidationUtil.isNull(iuFriend.getId(),"IuFriend","id",id);
        return iuFriendMapper.toDto(iuFriend);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IuFriendDto create(IuFriend resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return iuFriendMapper.toDto(iuFriendRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IuFriend resources) {
        IuFriend iuFriend = iuFriendRepository.findById(resources.getId()).orElseGet(IuFriend::new);
        ValidationUtil.isNull( iuFriend.getId(),"IuFriend","id",resources.getId());
        iuFriend.copy(resources);
        iuFriendRepository.save(iuFriend);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            iuFriendRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<IuFriendDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (IuFriendDto iuFriend : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("姓名", iuFriend.getName());
            map.put("别名", iuFriend.getNickname());
            map.put("英文名", iuFriend.getEname());
            map.put("生日", iuFriend.getBirthday());
            map.put("手机号码", iuFriend.getPhone());
            map.put("电子邮箱", iuFriend.getEmail());
            map.put("籍贯", iuFriend.getHometown());
            map.put("公司组织", iuFriend.getOrganization());
            map.put("居住地", iuFriend.getResidence());
            map.put("婚姻状况", iuFriend.getMarry());
            map.put("爱好", iuFriend.getHobby());
            map.put("个人站点", iuFriend.getWebsite());
            map.put("来源", iuFriend.getSource());
            map.put("创建人", iuFriend.getCreateBy());
            map.put("更新人", iuFriend.getUpdateBy());
            map.put("创建时间", iuFriend.getCreateTime());
            map.put("更新时间", iuFriend.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}