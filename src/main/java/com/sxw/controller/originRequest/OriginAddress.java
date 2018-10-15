package com.sxw.controller.originRequest; /*
 * @(#)OriginAddress.java 1.0 2018/10/15
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/10/15
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ToString
@Data
public class OriginAddress {


    /**
     * 省

     */
    @NotEmpty(message = "省不能为空")
    private String c001;
    /**
     * 市
     */
    private String c002;
    /**
     * 区
     */
    private String c003;
    /**
     * 街道/镇
     */
    private String c004;
    /**
     * 详细地址
     */
    @NotEmpty(message = "详细地址不能为空")
    private String c005;





}
