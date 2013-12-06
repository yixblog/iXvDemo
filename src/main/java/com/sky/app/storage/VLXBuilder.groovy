package com.sky.app.storage

import com.alibaba.fastjson.JSONObject
import com.sky.app.core.IVLXBuilder
import com.sky.app.dao.IRepository
import com.sky.vdk.vlx.generator.VLXGenerator
import com.sky.vdk.vlx.generator.nodedata.EndNodeBean
import com.sky.vdk.vlx.generator.nodedata.NodeBean
import org.springframework.stereotype.Service

import javax.annotation.Resource

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-8
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
@Service("vlxBuilder")
class VLXBuilder implements IVLXBuilder {
    @Resource(name = "memoryRepository")
    IRepository repository;

    @Override
    String buildPersonVLX() {
        VLXGenerator generator = new VLXGenerator();
        JSONObject person = repository.getOnePerson();
        generator.addEnd("impperson", [identityProperty: person['id'], idcard: person['idcard'], name: person['name']])
        return generator.generateVLX()
    }

    @Override
    String appendPersonPropertyVLX(String personId) {
        VLXGenerator generator = new VLXGenerator();
        JSONObject personObj = repository.getOnePerson();
        NodeBean person = generator.addEnd("impperson", [identityProperty: personObj['id'], idcard: personObj['idcard'], name: personObj['name']]);
        NodeBean carProperty = generator.addEnd("personproperty", [identityProperty: 1, label: "车辆信息", code: "CARINF", personId: personObj['idcard']]);
        NodeBean hotelProperty = generator.addEnd("personproperty", [identityProperty: 2, label: "旅馆信息", code: "HOTEL", personId: personObj['idcard']]);
        generator.connectNodes("normal", person, carProperty, [:]);
        generator.connectNodes("normal", person, hotelProperty, [:]);
        return generator.generateVLX();
    }

    @Override
    String appendHotelInfo(String personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean hotelProperty = generator.addEnd("personproperty", [identityProperty: 2, label: "旅馆信息", code: "HOTEL", personId: personId]);
        List<JSONObject> hotelRecords = repository.findHotelRecords(personId);
        hotelRecords.each { record ->
            NodeBean recordNode = generator.addEnd("hotelRecord", [identityProperty: "${record['date']}_${record['hotelName']}", hotelName: record['hotelName'], hotelId: record['hotelId'], roomNum: record['roomNum'], date: record['date']]);
            generator.connectNodes("liveInHotel", hotelProperty, recordNode, [date: record['date']]);
        }
        return generator.generateVLX()
    }

    @Override
    String appendCars(String personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean carProperty = generator.addEnd("personproperty", [identityProperty: 1, label: "车辆信息", code: "CARINF", personId: personId]);
        List<JSONObject> cars = repository.findPersonCars(personId);
        cars.each { car ->
            NodeBean carNode = generator.addEnd("car", [identityProperty: car['id'], plate: car['plate'], brand: car['brand']]);
            generator.connectNodes('normal', carProperty, carNode, [:]);
        }
        return generator.generateVLX();
    }

    @Override
    String appendTrafficAccidents(String carId) {
        VLXGenerator generator = new VLXGenerator();
        JSONObject car = repository.findCar(carId);
        if(car==null){
            return "";
        }
        EndNodeBean carEnd = generator.addEnd("car", [identityProperty: car['id'], plate: car['plate'], brand: car['brand']]);
        List<JSONObject> accidents = repository.findAccidentByCar(carId);
        accidents.each { accident ->
            EndNodeBean accidentNode = generator.addEnd("trafficAccident", [identityProperty: accident['id'], time: accident['time'], location: accident['location']]);
            generator.connectNodes("inAccident", carEnd, accidentNode, [time: accident['time']]);
        }
        return generator.generateVLX();
    }

    @Override
    JSONObject getEndNodes() {
        JSONObject obj = [:];
        def generator = new VLXGenerator();
        obj["config"] = generator.getEndConfigs();
        obj;
    }
}
