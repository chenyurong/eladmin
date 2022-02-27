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
import me.zhengjie.iu.domain.IuFriend;
import me.zhengjie.iu.service.IuFriendService;
import me.zhengjie.iu.service.dto.IuFriendQueryCriteria;
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
* @author Ronald Chan
* @date 2022-02-27
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "朋友列表管理")
@RequestMapping("/api/iuFriend")
public class IuFriendController {

    private final IuFriendService iuFriendService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('iuFriend:list')")
    public void exportIuFriend(HttpServletResponse response, IuFriendQueryCriteria criteria) throws IOException {
        iuFriendService.download(iuFriendService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询朋友列表")
    @ApiOperation("查询朋友列表")
    @PreAuthorize("@el.check('iuFriend:list')")
    public ResponseEntity<Object> queryIuFriend(IuFriendQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(iuFriendService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增朋友列表")
    @ApiOperation("新增朋友列表")
    @PreAuthorize("@el.check('iuFriend:add')")
    public ResponseEntity<Object> createIuFriend(@Validated @RequestBody IuFriend resources){
        return new ResponseEntity<>(iuFriendService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改朋友列表")
    @ApiOperation("修改朋友列表")
    @PreAuthorize("@el.check('iuFriend:edit')")
    public ResponseEntity<Object> updateIuFriend(@Validated @RequestBody IuFriend resources){
        iuFriendService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除朋友列表")
    @ApiOperation("删除朋友列表")
    @PreAuthorize("@el.check('iuFriend:del')")
    public ResponseEntity<Object> deleteIuFriend(@RequestBody Long[] ids) {
        iuFriendService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}