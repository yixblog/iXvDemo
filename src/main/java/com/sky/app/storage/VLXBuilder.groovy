package com.sky.app.storage

import com.sky.app.core.IVLXBuilder
import com.sky.app.dao.*
import com.sky.app.dao.beans.*
import com.sky.vdk.vlx.generator.VLXGenerator
import com.sky.vdk.vlx.generator.nodedata.EndNodeBean
import com.sky.vdk.vlx.generator.nodedata.LinkDirection
import com.sky.vdk.vlx.generator.nodedata.LinkNodeBean
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
    @Resource(name = 'personDAO')
    private IPersonDAO personDAO;
    @Resource(name = 'carDAO')
    private ICarDAO carDAO;
    @Resource(name = 'hotelDAO')
    private IHotelDAO hotelDAO;
    @Resource(name = 'internetCafeDAO')
    private IInternetCafeDAO internetCafeDAO;
    @Resource(name = 'trafficOffenceDAO')
    private ITrafficOffenceDAO trafficOffenceDAO;
    @Resource(name = 'reformPlanDAO')
    private IReformPlanDAO reformPlanDAO;
    @Resource(name = 'justiceEducationAttendDAO')
    private IJusticeEducationAttendDAO justiceEducationAttendDAO;
    @Resource(name = 'volunteerWorkDAO')
    private IVolunteerWorkDAO volunteerWorkDAO;

    @Override
    String findPerson(int id) {
        Person person = personDAO.findPersonById(id);
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean end = generator.addEndByPojo(person);
        return generator.generateVLX();
    }

    @Override
    String expandPersonParams(int id) {
        Person person = personDAO.findPersonById(id);
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean personNode = generator.addEndByPojo(person);
        EndNodeBean networkProperty = generator.addEnd('personProperty', [identityProperty: 'network', label: '上网信息', uri: 'data/vlx/networks.vlx', personid: id.toString()]);
        networkProperty.configImage('VLImages/gifs/new/WWW.png', 32, 32);
        generator.connectNodes('hasProperty', personNode, networkProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean hotelProperty = generator.addEnd('personProperty', [identityProperty: 'hotel', label: '旅馆信息', uri: 'data/vlx/hotelRecord.vlx', personid: id.toString()]);
        hotelProperty.configImage('VLImages/gifs/new/Town.png', 32, 32)
        generator.connectNodes('hasProperty', personNode, hotelProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean carProperty = generator.addEnd('personProperty', [identityProperty: 'cars', label: '车辆信息', uri: 'data/vlx/cars.vlx', personid: id.toString()]);
        carProperty.configImage('VLImages/gifs/new/Drivers License.png', 32, 32);
        generator.connectNodes('hasProperty', personNode, carProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean trafficProperty = generator.addEnd('personProperty', [identityProperty: 'traffic', label: '交通违法信息', uri: 'data/vlx/traffic.vlx', personid: id.toString()]);
        trafficProperty.configImage('VLImages/gifs/new/Road Block.png', 32, 32);
        generator.connectNodes('hasProperty', personNode, trafficProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean justiceProperty = generator.addEnd('personProperty', [identityProperty: 'justice', label: '司法信息', uri: 'data/vlx/justice.vlx', personid: id.toString()]);
        justiceProperty.configImage('VLImages/gifs/new/Profmale.png', 32, 32);
        generator.connectNodes('hasProperty', personNode, justiceProperty, [:]).setDirection(LinkDirection.forward);
        return generator.generateVLX();
    }

    @Override
    String expandCarInfo(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean carProperty = generator.addEnd('personProperty', [identityProperty: 'cars', label: '车辆信息', uri: 'data/vlx/cars.vlx', personid: personId.toString()]);
        carProperty.configImage('VLImages/gifs/new/Drivers License.png', 32, 32);

        List<Car> cars = carDAO.listDriverCars(personId);
        for (Car car : cars) {
            EndNodeBean carEnd = generator.addEndByPojo(car);
            generator.connectNodes('hasProperty', carProperty, carEnd, [:]);
        }
        return generator.generateVLX();
    }

    @Override
    String expandHotelRecord(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean hotelProperty = generator.addEnd('personProperty', [identityProperty: 'hotel', label: '旅馆信息', uri: 'data/vlx/hotelRecord.vlx', personid: personId.toString()]);
        hotelProperty.configImage('VLImages/gifs/new/Town.png', 32, 32);

        List<HotelLiveRecord> records = hotelDAO.listLiveRecordByPerson(personId);
        for (HotelLiveRecord record : records) {
            EndNodeBean recEnd = generator.addEndByPojo(record);
            generator.connectNodes('liveInHotel', hotelProperty, recEnd, [date: record.getStartDate()]);
        }
        return generator.generateVLX();
    }

    @Override
    String expandWebCafeRecord(int personId) {
        VLXGenerator generator = new VLXGenerator();

        EndNodeBean networkProperty = generator.addEnd('personProperty', [identityProperty: 'network', label: '上网信息', uri: 'data/vlx/networks.vlx', personid: personId.toString()]);
        networkProperty.configImage('VLImages/gifs/new/WWW.png', 32, 32);

        List<InternetCafeRecord> cafeRecords = internetCafeDAO.listCafeRecordByPerson(personId);
        for (InternetCafeRecord rec : cafeRecords) {
            InternetCafe cafe = rec.getCafe();
            EndNodeBean cafeNode = generator.addEndByPojo(cafe);
            generator.connectNodes('netcafelog', networkProperty, cafeNode, [identityProperty: rec.getId().toString(), time: rec.getStartTime()]);
        }
        return generator.generateVLX();
    }

    @Override
    String expandTrafficOffences(int personId) {
        VLXGenerator generator = new VLXGenerator();

        EndNodeBean trafficProperty = generator.addEnd('personProperty', [identityProperty: 'traffic', label: '交通违法信息', uri: 'data/vlx/traffic.vlx', personid: personId.toString()]);
        trafficProperty.configImage('VLImages/gifs/new/Road Block.png', 32, 32);

        List<TrafficOffence> offences = trafficOffenceDAO.listDriverTrafficOffences(personId);
        offences.each {
            EndNodeBean offenceEnd = generator.addEndByPojo(it);
            generator.connectNodes('inAccident', trafficProperty, offenceEnd, [time: it.getTime()]);
        }
        return generator.generateVLX();
    }

    @Override
    String expandJustice(int personId) {
        VLXGenerator generator = new VLXGenerator();

        EndNodeBean justiceProperty = generator.addEnd('personProperty', [identityProperty: 'justice', label: '司法信息', uri: 'data/vlx/justice.vlx', personid: personId.toString()]);
        justiceProperty.configImage('VLImages/gifs/new/Profmale.png', 32, 32);

        EndNodeBean reformProperty = generator.addEnd('personProperty', [identityProperty: 'reform', label: '矫正方案', uri: 'data/vlx/reform.vlx', personid: personId.toString()]);
        reformProperty.configImage('VLImages/gifs/new/spreadsheetdoc.png', 32, 32);
        generator.connectNodes('hasProperty', justiceProperty, reformProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean educationProperty = generator.addEnd('personProperty', [identityProperty: 'educate', label: '集中学习', uri: 'data/vlx/education.vlx', personid: personId.toString()]);
        educationProperty.configImage('VLImages/gifs/new/school.png', 32, 32);
        generator.connectNodes('hasProperty', justiceProperty, educationProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean volunteerProperty = generator.addEnd('personProperty', [identityProperty: 'volunteer', label: '公益劳动', uri: 'data/vlx/volunteer.vlx', personid: personId.toString()]);
        volunteerProperty.configImage('VLImages/gifs/new/Trash.png', 32, 32);
        generator.connectNodes('hasProperty', justiceProperty, volunteerProperty, [:]).setDirection(LinkDirection.forward)
        return generator.generateVLX();
    }

    @Override
    String expandReform(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean reformProperty = generator.addEnd('personProperty', [identityProperty: 'reform', label: '矫正方案', uri: 'data/vlx/reform.vlx', personid: personId.toString()]);
        reformProperty.configImage('VLImages/gifs/new/spreadsheetdoc.png', 32, 32);

        List<JusticeReformPlan> plans = reformPlanDAO.listPlansOfPerson(personId);
        plans.each {
            EndNodeBean planEnd = generator.addEndByPojo(it);
            generator.connectNodes('normal', reformProperty, planEnd, [:])
        }
        return generator.generateVLX();
    }

    @Override
    String expandEducation(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean educationProperty = generator.addEnd('personProperty', [identityProperty: 'educate', label: '集中学习', uri: 'data/vlx/education.vlx', personid: personId.toString()]);
        educationProperty.configImage('VLImages/gifs/new/school.png', 32, 32);

        List<JusticeEducationAttended> educations = justiceEducationAttendDAO.findEducationAttendByPerson(personId);
        educations.each {
            EndNodeBean eduEnd = generator.addEndByPojo(it);
            generator.connectNodes('normal', educationProperty, eduEnd, [:]);
        }
        return generator.generateVLX();
    }

    @Override
    String expandVolunteer(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean volunteerProperty = generator.addEnd('personProperty', [identityProperty: 'volunteer', label: '公益劳动', uri: 'data/vlx/volunteer.vlx', personid: personId.toString()]);
        volunteerProperty.configImage('VLImages/gifs/new/Trash.png', 32, 32);

        List<JusticeVolunteerWork> volunteers = volunteerWorkDAO.listPersonVolunteerWorks(personId);
        volunteers.each {
            EndNodeBean volNode = generator.addEndByPojo(it);
            generator.connectNodes('normal', volunteerProperty, volNode, [:]);
        }
        return generator.generateVLX();
    }
}
