package com.sky.app.storage

import com.sky.app.core.IVLXBuilder
import com.sky.app.dao.*
import com.sky.app.dao.pojo.AirRecord
import com.sky.app.dao.pojo.CafeRecord
import com.sky.app.dao.pojo.LiveRecord
import com.sky.app.dao.pojo.Person
import com.sky.app.dao.pojo.PunishRecord
import com.sky.vdk.vlx.generator.VLXGenerator
import com.sky.vdk.vlx.generator.nodedata.EndNodeBean
import com.sky.vdk.vlx.generator.nodedata.ImageConfig
import com.sky.vdk.vlx.generator.nodedata.LinkDirection
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
    private String imageURLPerfix = "";
    @Resource
    private IPersonDAO personDAO;
    @Resource
    private IAirRecordDAO airRecordDAO;
    @Resource
    private ICafeRecordDAO cafeRecordDAO;
    @Resource
    private IFlightDAO flightDAO;
    @Resource
    private ILiveRecordDAO liveRecordDAO;
    @Resource
    private IHotelDAO hotelDAO;
    @Resource
    private INetCafeDAO netCafeDAO;
    @Resource
    private IPunishRecordDAO punishRecordDAO;

    @Override
    String findPerson(int id) {
        Person person = new Person(id: id)
        person = personDAO.findOneObject(person, 'id');
        VLXGenerator generator = new VLXGenerator();
        generator.addEndByPojo(person)
        return generator.generateVLX();
    }

    @Override
    String expandPersonParams(int id) {
        VLXGenerator generator = new VLXGenerator();
        Person p = new Person(id: id);
        p = personDAO.findOneObject(p, 'id');
        EndNodeBean personEnd = generator.addEndByPojo(p);

        EndNodeBean flightProperties = createFlightPropertiesNode(generator, id)
        generator.connectNodes('hasProperty', personEnd, flightProperties, [:]).setDirection(LinkDirection.forward)

        EndNodeBean hotelProperties = createHotelPropertiesNode(generator, id)
        generator.connectNodes('hasProperty', personEnd, hotelProperties, [:]).setDirection(LinkDirection.forward);

        EndNodeBean carProperty = generator.addEnd('personProperty', [identityProperty: 'cars', label: '车辆信息', personid: id.toString()]);
        carProperty.configImage('VLImages/gifs/new/Drivers License.png', 32, 32);
        generator.connectNodes('hasProperty', personEnd, carProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean punishProperties = createPunishPropertiesNode(generator, id)
        generator.connectNodes('hasProperty', personEnd, punishProperties, [:]).setDirection(LinkDirection.forward);

        EndNodeBean netProperties = createNetRecordPropertiesNode(generator, id)
        generator.connectNodes('hasProperty', personEnd, netProperties, [:]).setDirection(LinkDirection.forward);

        EndNodeBean justiceProperty = createJusticePropertiesNode(generator, id)
        generator.connectNodes('hasProperty', personEnd, justiceProperty, [:]).setDirection(LinkDirection.forward);
        return generator.generateVLX();
    }

    private EndNodeBean createJusticePropertiesNode(VLXGenerator generator, int id) {
        EndNodeBean justiceProperty = generator.addEnd('personProperty', [identityProperty: 'justice', label: '司法信息', uri: 'data/vlx/justice.vlx', personid: id.toString()]);
        justiceProperty.configImage('VLImages/gifs/new/Profmale.png', 32, 32);
        justiceProperty
    }

    private EndNodeBean createNetRecordPropertiesNode(VLXGenerator generator, int id) {
        EndNodeBean netProperties = generator.addEnd('personProperty', [identityProperty: 'prop-04', label: '上网记录', uri: 'data/vlx/netcafe.vlx', personid: "$id"]);
        netProperties.configImage("VLImages/gifs/new/Computer Log.png", 32, 32)
        netProperties
    }

    private EndNodeBean createPunishPropertiesNode(VLXGenerator generator, int id) {
        EndNodeBean punishProperties = generator.addEnd('personProperty', [identityProperty: 'prop-03', label: '处罚记录', uri: 'data/vlx/punish.vlx', personid: "$id"]);
        punishProperties.configImage('VLImages/gifs/new/prison.png', 32, 32)
        punishProperties
    }

    private EndNodeBean createHotelPropertiesNode(VLXGenerator generator, int id) {
        EndNodeBean hotelProperties = generator.addEnd('personProperty', [identityProperty: 'prop-02', label: '住宿信息', uri: 'data/vlx/hotelRecord.vlx', personid: "$id"]);
        hotelProperties.configImage('VLImages/gifs/new/Hospbed.png', 32, 32)
        hotelProperties
    }

    private EndNodeBean createFlightPropertiesNode(VLXGenerator generator, int id) {
        EndNodeBean flightProperties = generator.addEnd('personProperty', [identityProperty: 'prop-01', label: '航班信息', uri: 'data/vlx/flights.vlx', personid: "$id"]);
        flightProperties.configImage('VLImages/gifs/new/terminal.png', 32, 32)
        flightProperties
    }

    @Override
    String expandHotelRecord(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean hotelProp = createHotelPropertiesNode(generator, personId);
        Person p = new Person(id: personId);
        LiveRecord queryExample = new LiveRecord(person: p);
        List<LiveRecord> lives = liveRecordDAO.findMany(queryExample, 'person');
        for (LiveRecord record : lives) {
            EndNodeBean hotelNode = generator.addEndByPojo(record.getHotel());
            generator.connectNodes('liveInHotel', hotelProp, hotelNode, [date: record.getEnterTimeString(), identityProperty: "live-${record.personId}-${record.hotelId}-${record.enterTimeString}"])
        }
        return generator.generateVLX();
    }

    @Override
    String expandJustice(int personId) {
        VLXGenerator generator = new VLXGenerator();

        EndNodeBean justiceProperty = createJusticePropertiesNode(generator, personId)

        EndNodeBean reformProperty = generator.addEnd('personProperty', [identityProperty: 'reform', label: '矫正方案', personid: personId.toString()]);
        reformProperty.configImage('VLImages/gifs/new/spreadsheetdoc.png', 32, 32);
        generator.connectNodes('hasProperty', justiceProperty, reformProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean educationProperty = generator.addEnd('personProperty', [identityProperty: 'educate', label: '集中学习', personid: personId.toString()]);
        educationProperty.configImage('VLImages/gifs/new/school.png', 32, 32);
        generator.connectNodes('hasProperty', justiceProperty, educationProperty, [:]).setDirection(LinkDirection.forward);

        EndNodeBean volunteerProperty = generator.addEnd('personProperty', [identityProperty: 'volunteer', label: '公益劳动', personid: personId.toString()]);
        volunteerProperty.configImage('VLImages/gifs/new/Trash.png', 32, 32);
        generator.connectNodes('hasProperty', justiceProperty, volunteerProperty, [:]).setDirection(LinkDirection.forward)
        return generator.generateVLX();
    }

    @Override
    String expandWebCafeRecord(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean webProp = createNetRecordPropertiesNode(generator, personId);
        Person examplePerson = new Person(id: personId)
        CafeRecord example = new CafeRecord(person: examplePerson)
        List<CafeRecord> records = cafeRecordDAO.findMany(example, 'person');
        for (CafeRecord record : records) {
            EndNodeBean cafeNode = generator.addEndByPojo(record.getCafe());
            generator.connectNodes('netcafelog', webProp, cafeNode, [identityProperty: "net-$record.personId-$record.cafeId-$record.onlineTimeString", time: record.getOnlineTimeString(), ip: record.getIp()])
        }
        return generator.generateVLX();
    }

    @Override
    String expandFlightRecord(int personId) {
        VLXGenerator generator = new VLXGenerator();
        EndNodeBean flightProp = createFlightPropertiesNode(generator, personId);
        Person examplePerson = new Person(id: personId);
        AirRecord example = new AirRecord(person: examplePerson);
        List<AirRecord> records = airRecordDAO.findMany(example, 'person');
        for (AirRecord rec : records) {
            EndNodeBean flightNode = generator.addEndByPojo(rec.getFlight());
            generator.connectNodes('onflight', flightProp, flightNode, [seat: rec.getSeat()])
        }
        return generator.generateVLX();
    }

    @Override
    String expandPunishRecord(int personId) {
        VLXGenerator generator = new VLXGenerator()
        EndNodeBean punishProp = createPunishPropertiesNode(generator, personId)
        Person exP = new Person(id: personId)
        PunishRecord example = new PunishRecord(person: exP)
        List<PunishRecord> records = punishRecordDAO.findMany(example, 'person');
        for (PunishRecord rec : records) {
            EndNodeBean punish = generator.addEndByPojo(rec);
            generator.connectNodes('normal', punishProp, punish, [:])
        }
        return generator.generateVLX()
    }
}
