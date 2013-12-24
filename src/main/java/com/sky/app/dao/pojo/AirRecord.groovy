package com.sky.app.dao.pojo

/**
 * Created by Yixian on 13-12-22.
 */
class AirRecord implements IDatabaseBean{
    int id;
    Flight flight;
    Person person;
    String bdno;
    String seat;
    String seatClass;
    int bagUnit;

    int getPersonId(){
        return person?.getId();
    }

    int getFlightId(){
        return flight?.getId();
    }

    @Override
    boolean pkAutoIncrement() {
        return true;
    }
}
