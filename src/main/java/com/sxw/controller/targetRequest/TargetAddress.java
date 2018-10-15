package com.sxw.controller.targetRequest; /*
 * @(#)Address.java 1.0 2018/10/15
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
public class TargetAddress {



    /**

     * 省
     */
    @NotEmpty(message = "省不能为空")
    private String provincename;
    /**
     * 市
     */
    private String cityname;
    /**
     * 区
     */
    private String areaname;
    /**
     * 街道/镇
     */
    private String townname;
    /**
     * 详细地址
     */
    @NotEmpty(message = "详细地址不能为空")
    private String detailaddress;


    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getTownname() {
        return townname;
    }

    public void setTownname(String townname) {
        this.townname = townname;
    }

    public String getDetailaddress() {
        return detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

}
