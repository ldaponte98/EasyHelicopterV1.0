package com.example.easyhelicopter.BD;

import com.example.easyhelicopter.Models.Ciudad;
import com.example.easyhelicopter.Models.Puerto;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static List<Ciudad> BD_Ciudades = new ArrayList<>();
    public static List<Puerto> BD_Puertos = new ArrayList<>();


    public static void star(){
        Ciudad c1 = new Ciudad("Medellin");
        Puerto p1c1 = new Puerto("Helipuerto Clínica las Vegas",6.203394, -75.576239);
        Puerto p2c1 = new Puerto("Helipuerto Hospital General",6.234881, -75.572711);
        Puerto p3c1 = new Puerto("Helipuerto Banco de la Republica",6.249090, -75.568323);


        c1.getPuertos().add(p1c1);
        c1.getPuertos().add(p2c1);
        c1.getPuertos().add(p3c1);

        Ciudad c2 = new Ciudad("Bogota");
        c2.getPuertos().add(new Puerto("Helipuerto Hospital Cardiovascular",4.581598, -74.200522));
        c2.getPuertos().add(new Puerto("Aeropuerto Internacional El Dorado",4.697149, -74.141116));

        Ciudad c3 = new Ciudad("Barranquilla");
        c3.getPuertos().add(new Puerto("Aeropuerto Internacional Ernesto Cortissoz",10.886539, -74.776479));



        BD_Ciudades.add(c1);
        BD_Ciudades.add(c2);
        BD_Ciudades.add(c3);
        BD_Puertos.addAll(c1.getPuertos());
        BD_Puertos.addAll(c2.getPuertos());
        BD_Puertos.addAll(c3.getPuertos());

    }
}
