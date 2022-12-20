package com.devopsusach2020.rest;

import com.devopsusach2020.model.Mundial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestDataTest {

    RestData objRestData = new RestData();

    @Test
    void getTotalMundial_thenStatus200() throws Exception  {
        Mundial objMundial = new Mundial();
        objMundial = this.objRestData.getTotalMundial();
        Assertions.assertNotEquals(objMundial.getTotalConfirmed(), 0);
        Assertions.assertNotEquals(objMundial.getTotalDeaths(), 0);
        Assertions.assertEquals(objMundial.getTotalRecovered(), 0);
    }
}
