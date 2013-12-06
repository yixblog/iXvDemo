package com.sky.app.memory

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.sky.app.dao.IRepository
import org.apache.log4j.Logger
import org.springframework.stereotype.Repository

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-12-2
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
@Repository('memoryRepository')
class MemoryRepository implements IRepository {
    private Map<String, JSONObject> userMap = [:];
    private List<JSONObject> hotelRecordMap = [];
    private List<JSONObject> carRecords = [];
    private List<JSONObject> accidents = [];
    private List<JSONObject> networks = [];
    private Logger log = Logger.getLogger(getClass());

    MemoryRepository() {
        initData();
    }

    private void initData() {
        userMap['2333'] = [id: '2333', name: "施自强", idcard: '320100198706110304'] as JSONObject

        hotelRecordMap.add([id: 110, hotelId: '039', hotelName: '土豪大酒店', roomNum: 202, date: '2013-11-10', personID: "320100198706110304"] as JSONObject);
        hotelRecordMap.add([id: 113, hotelId: '039', hotelName: '土豪大酒店', roomNum: 301, date: '2013-11-12', personID: "320100198706110304"] as JSONObject);
        hotelRecordMap.add([id: 114, hotelId: '155', hotelName: '新城宾馆', date: '2013-11-15', roomNum: 100, personID: "320100198706110304"] as JSONObject);
        hotelRecordMap.add([id: 115, hotelId: '039', hotelName: '土豪大酒店', date: '2013-11-19', roomNum: 223, personID: "320100198706110304"] as JSONObject);

        carRecords.add([id: 2209, plate: "苏A12345", brand: "路虎", driver: "320100198706110304"] as JSONObject);
        carRecords.add([id: 2220, plate: "苏A54321", brand: "布加迪", driver: "320100196603110511"] as JSONObject);

        accidents.add([id: 111, time: "2013-11-12 10:18:00", location: "北京西路", cars: [2209, 2220]] as JSONObject);
        accidents.add([id: 112, time: "2013-11-20 23:00:21", location: "中山南路", cars: [2209]] as JSONObject);
        accidents.add([id: 115, time: "2013-11-27 23:18:21", location: "大桥南路", cars: [2209]] as JSONObject);
    }

    @Override
    JSONObject getOnePerson() {
        return userMap['2333'].clone() as JSONObject;
    }

    @Override
    List<JSONObject> findHotelRecords(String personId) {
        List<JSONObject> resultList = [];
        hotelRecordMap.each { record ->
            if (record['personID'] == personId) {
                resultList.add(record.clone() as JSONObject);
            }
        }
        return resultList;
    }

    @Override
    List<JSONObject> findPersonCars(String personId) {
        List<JSONObject> resultList = [];
        carRecords.each { car ->
            if (car["driver"] == personId) {
                resultList.add(car.clone() as JSONObject)
            }
        }
        return resultList;
    }

    @Override
    JSONObject findCar(String carId) {
        JSONObject car = null;
        carRecords.find { record ->
            log.debug("carid:$carId,recordid:${record['id']}")
            if (record.getString('id') == carId) {
                car = record;
                return true;
            }
            return false;
        }
        return car;
    }

    @Override
    List<JSONObject> findAccidentByCar(String carId) {
        List<JSONObject> resultList = [];
        accidents.each { accident ->
            if (checkContains(accident.getJSONArray("cars"), carId)) {
                resultList.add(accident.clone() as JSONObject)
            }
        }
        return resultList;
    }

    private boolean checkContains(JSONArray array, String item) {
        boolean contains = false;
        array.find {
            if (it.toString() == item) {
                contains = true;
                return true;
            }
            return false;
        }
        contains;
    }
}
