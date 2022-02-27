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
package me.zhengjie.iu.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @website https://el-admin.vip
* @description /
* @author Ronald Chan
* @date 2022-02-27
**/
@Data
public class IuFriendDto implements Serializable {

    /** ID */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /** 姓名 */
    private String name;

    /** 别名 */
    private String nickname;

    /** 英文名 */
    private String ename;

    /** 生日 */
    private Timestamp birthday;

    /** 手机号码 */
    private String phone;

    /** 电子邮箱 */
    private String email;

    /** 籍贯 */
    private String hometown;

    /** 公司组织 */
    private String organization;

    /** 居住地 */
    private String residence;

    /** 婚姻状况 */
    private String marry;

    /** 爱好 */
    private String hobby;

    /** 个人站点 */
    private String website;

    /** 来源 */
    private String source;

    /** 创建人 */
    private String createBy;

    /** 更新人 */
    private String updateBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}