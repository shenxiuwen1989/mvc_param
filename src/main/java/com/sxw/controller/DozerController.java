package com.sxw.controller;

import com.sxw.controller.originRequest.OriginAddress;
import com.sxw.controller.targetRequest.TargetAddress;
import com.sxw.service.DozerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/dozer")
@Slf4j
public class DozerController {

    @Autowired
    private DozerService dozerService;

    /**
     *
     * 请求参数：{"c001":"湖南","c002":"邵阳","c003":"大祥区","c004":"大运街道","c005":"36号"}
     * 打印的效果：
     *  请求参数：[OriginAddress(c001=湖南, c002=邵阳, c003=大祥区, c004=大运街道, c005=36号)]
     *  转换后参数：[TargetAddress(provincename=湖南, cityname=邵阳, areaname=大祥区, townname=大运街道, detailaddress=36号)]
     */
    @RequestMapping(value = "/converOriginRequest",method = RequestMethod.POST)
    public TargetAddress converOriginRequest(@Valid @RequestBody OriginAddress originAddress){
        log.info("请求参数：[{}]",originAddress);

        TargetAddress targetAddress = dozerService.convert(originAddress,TargetAddress.class);

        log.info("转换后参数：[{}]",targetAddress);

        return targetAddress;
    }

}
