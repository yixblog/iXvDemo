package com.sky.app.web

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
@RequestMapping("/data/vlx")
class VLXController {
    @Resource(name = "vlxBuilder")
    private IVLXBuilder vlxBuilder;

    @RequestMapping('/person.vlx')
    @ResponseBody
    String getPersonVlx(@RequestParam int personId) {
        return vlxBuilder.findPerson(personId);
    }

    @RequestMapping('/personProperties.vlx')
    @ResponseBody
    String getPersonProperties(@RequestParam String params) {
        JSONObject paramObj = JSONObject.parseObject(params);
        int personId = paramObj.getIntValue('identityProperty');
        return vlxBuilder.expandPersonParams(personId);
    }

    @RequestMapping('/netcafe.vlx')
    @ResponseBody
    String getPersonNetCafeLogs(@RequestParam String params) {
        return vlxBuilder.expandWebCafeRecord(solvePersonId(params));
    }

    @RequestMapping('/hotelRecord.vlx')
    @ResponseBody
    String listPersonHotelRecords(@RequestParam String params) {
        return vlxBuilder.expandHotelRecord(solvePersonId(params));
    }

    @RequestMapping('/flights.vlx')
    @ResponseBody
    String listPersonFlights(@RequestParam String params) {
        return vlxBuilder.expandFlightRecord(solvePersonId(params))
    }

    @RequestMapping('/punish.vlx')
    @ResponseBody
    String listPunishment(@RequestParam String params) {
        return vlxBuilder.expandPunishRecord(solvePersonId(params))
    }

    @RequestMapping("/justice.vlx")
    @ResponseBody
    String expandJustice(@RequestParam String params){
        return vlxBuilder.expandJustice(solvePersonId(params))
    }

    private int solvePersonId(String params) {
        JSONObject paramObj = JSONObject.parseObject(params);
        paramObj.getIntValue('personid');
    }
}
