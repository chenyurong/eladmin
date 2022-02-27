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
package me.zhengjie.iu.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author Ronald Chan
* @date 2022-02-27
**/
@Entity
@Data
@Table(name="iu_friend")
public class IuFriend implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "nickname")
    @ApiModelProperty(value = "别名")
    private String nickname;

    @Column(name = "ename")
    @ApiModelProperty(value = "英文名")
    private String ename;

    @Column(name = "birthday")
    @ApiModelProperty(value = "生日")
    private Timestamp birthday;

    @Column(name = "phone",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @Column(name = "email")
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @Column(name = "hometown")
    @ApiModelProperty(value = "籍贯")
    private String hometown;

    @Column(name = "organization")
    @ApiModelProperty(value = "公司组织")
    private String organization;

    @Column(name = "residence")
    @ApiModelProperty(value = "居住地")
    private String residence;

    @Column(name = "marry")
    @ApiModelProperty(value = "婚姻状况")
    private String marry;

    @Column(name = "hobby")
    @ApiModelProperty(value = "爱好")
    private String hobby;

    @Column(name = "website")
    @ApiModelProperty(value = "个人站点")
    private String website;

    @Column(name = "source")
    @ApiModelProperty(value = "来源")
    private String source;

    @Column(name = "tags")
    @ApiModelProperty(value = "标签列表")
    private String[] tags;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(IuFriend source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}