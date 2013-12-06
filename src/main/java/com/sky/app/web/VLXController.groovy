package com.sky.app.web

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.sky.app.core.IVLXBuilder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import javax.annotation.Resource

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-8
 * Time: 下午2:27
 */
@Controller
@RequestMapping("/vlx")
class VLXController {
    @Resource(name = "vlxBuilder")
    private IVLXBuilder vlxBuilder;


    @RequestMapping("/expandNode")
    @ResponseBody
    String expandVLXNode(@RequestParam String endType, @RequestParam String params) {
        JSONObject paramObject = JSON.parseObject(params);
        if (endType == "impperson") {
            return vlxBuilder.appendPersonPropertyVLX(paramObject.getString('identityProperty'));
        } else if (endType == "personproperty") {
            if (paramObject.getString('code') == "HOTEL") {
                return vlxBuilder.appendHotelInfo(paramObject.getString('personId'));
            } else if (paramObject.getString('code') == "CARINF") {
                return vlxBuilder.appendCars(paramObject.getString('personId'));
            }
        } else if (endType == "car") {
            return vlxBuilder.appendTrafficAccidents(paramObject.getString('identityProperty'));
        }
        return "";
    }

    @RequestMapping("/loadPerson")
    @ResponseBody
    String loadVlxString() {
        return vlxBuilder.buildPersonVLX();
    }

    @RequestMapping("/queryProperties")
    @ResponseBody
    String queryProperties(@RequestParam String personId) {
        return vlxBuilder.appendPersonPropertyVLX(personId);
    }

    @RequestMapping("/appendHotelRecords")
    @ResponseBody
    String appendHotelRecords(@RequestParam String personId) {
        return vlxBuilder.appendHotelInfo(personId);
    }

    @RequestMapping("/appendCars")
    @ResponseBody
    String appendCars(@RequestParam String personId) {
        return vlxBuilder.appendCars(personId);
    }

    @RequestMapping("/appendAccidents")
    @ResponseBody
    String appendAccidents(@RequestParam String carId) {
        return vlxBuilder.appendTrafficAccidents(carId)
    }

    @RequestMapping("/ends")
    @ResponseBody
    JSONObject getVLXEnds() {
        vlxBuilder.getEndNodes();
    }
}
