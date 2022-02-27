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

import me.zhengjie.iu.domain.IuTag;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.iu.repository.IuTagRepository;
import me.zhengjie.iu.service.IuTagService;
import me.zhengjie.iu.service.dto.IuTagDto;
import me.zhengjie.iu.service.dto.IuTagQueryCriteria;
import me.zhengjie.iu.service.mapstruct.IuTagMapper;
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
* @author Ronal Chan
* @date 2022-02-27
**/
@Service
@RequiredArgsConstructor
public class IuTagServiceImpl implements IuTagService {

    private final IuTagRepository iuTagRepository;
    private final IuTagMapper iuTagMapper;

    @Override
    public Map<String,Object> queryAll(IuTagQueryCriteria criteria, Pageable pageable){
        Page<IuTag> page = iuTagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(iuTagMapper::toDto));
    }

    @Override
    public List<IuTagDto> queryAll(IuTagQueryCriteria criteria){
        return iuTagMapper.toDto(iuTagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public IuTagDto findById(Long id) {
        IuTag iuTag = iuTagRepository.findById(id).orElseGet(IuTag::new);
        ValidationUtil.isNull(iuTag.getId(),"IuTag","id",id);
        return iuTagMapper.toDto(iuTag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IuTagDto create(IuTag resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return iuTagMapper.toDto(iuTagRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IuTag resources) {
        IuTag iuTag = iuTagRepository.findById(resources.getId()).orElseGet(IuTag::new);
        ValidationUtil.isNull( iuTag.getId(),"IuTag","id",resources.getId());
        iuTag.copy(resources);
        iuTagRepository.save(iuTag);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            iuTagRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<IuTagDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (IuTagDto iuTag : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("标签名", iuTag.getName());
            map.put("创建人", iuTag.getCreateBy());
            map.put("更新人", iuTag.getUpdateBy());
            map.put("创建时间", iuTag.getCreateTime());
            map.put("更新时间", iuTag.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}