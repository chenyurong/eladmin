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
package me.zhengjie.iu.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.iu.domain.IuTag;
import me.zhengjie.iu.service.IuTagService;
import me.zhengjie.iu.service.dto.IuTagQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Ronal Chan
* @date 2022-02-27
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "标签管理")
@RequestMapping("/api/iuTag")
public class IuTagController {

    private final IuTagService iuTagService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('iuTag:list')")
    public void exportIuTag(HttpServletResponse response, IuTagQueryCriteria criteria) throws IOException {
        iuTagService.download(iuTagService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询标签")
    @ApiOperation("查询标签")
    @PreAuthorize("@el.check('iuTag:list')")
    public ResponseEntity<Object> queryIuTag(IuTagQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(iuTagService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增标签")
    @ApiOperation("新增标签")
    @PreAuthorize("@el.check('iuTag:add')")
    public ResponseEntity<Object> createIuTag(@Validated @RequestBody IuTag resources){
        return new ResponseEntity<>(iuTagService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改标签")
    @ApiOperation("修改标签")
    @PreAuthorize("@el.check('iuTag:edit')")
    public ResponseEntity<Object> updateIuTag(@Validated @RequestBody IuTag resources){
        iuTagService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除标签")
    @ApiOperation("删除标签")
    @PreAuthorize("@el.check('iuTag:del')")
    public ResponseEntity<Object> deleteIuTag(@RequestBody Long[] ids) {
        iuTagService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}