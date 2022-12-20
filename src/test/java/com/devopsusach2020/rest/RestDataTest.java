package com.devopsusach2020.rest;

import com.devopsusach2020.model.Mundial;

import com.devopsusach2020.model.Pais;
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

    @Test
    void getTotalPais_thenStatus200() throws Exception  {
        Pais responsePais = new Pais();
        responsePais = this.objRestData.getTotalPais("chile");
        Assertions.assertNotEquals(responsePais.getDeaths(), 0);
        Assertions.assertNotEquals(responsePais.getConfirmed(), 0);
        Assertions.assertNotEquals(responsePais.getRecovered(), 0);
        Assertions.assertNotEquals(responsePais.getActive(), 0);
        Assertions.assertNotEquals(responsePais.getDate(), "");
        Assertions.assertEquals(responsePais.getMensaje(), "ok");
        Assertions.assertEquals(responsePais.getCountry(), "chile");
    }

    @Test
    void getTotalPais_thenStatus500() throws Exception  {
        Pais responsePais = new Pais();
        try{
            responsePais = this.objRestData.getTotalPais("eeuu");
        } catch(Exception e) {
            responsePais=null;
        }

        Assertions.assertNull(responsePais);
    }
}
