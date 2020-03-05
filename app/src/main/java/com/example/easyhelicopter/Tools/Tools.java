package com.example.easyhelicopter.Tools;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {

    public String FormatoOracion(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    public String FechaFormato1(Date fecha){//diciembre 3 de 2019
        SimpleDateFormat formateadorMesDia = new SimpleDateFormat("MMMM d");
        SimpleDateFormat formateadorAño = new SimpleDateFormat("yyyy");
        String MesDia = formateadorMesDia.format(fecha);
        MesDia = Character.toUpperCase(MesDia.charAt(0)) + MesDia.substring(1,MesDia.length());
        return MesDia + " de "+formateadorAño.format(fecha);
    }
    public String FechaFormato2(Date fecha){//dia-mes-año
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(fecha);
    }
    public String FechaFormato3(Date fecha){//año-mes-dia
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(fecha);
    }
    public String FechaFormato4(String fecha){//3 diciembre de 2019
        String[] f = fecha.split("-");
        String dia = f[0];
        String mes = f[1];
        String año = f[2];
        return dia + " de "+GetNameMes(mes)+ " de "+año;
    }
    public String FechaFormato5(String fecha){//3 diciembre de 2019

        String[] f = fecha.split("/");
        if (f.length==3){
        String dia = f[0];
        String mes = f[1];
        String año = f[2];
        return dia + " de "+GetNameMes(mes)+ " de "+año;
        }
        return "";
    }


    public String GetNameMes(String numero){
        switch (numero){
            case "01":
                return "Enero";
            case "02":
                return "Febrero";
            case "03":
                return "Marzo";
            case "04":
                return "Abril";
            case "05":
                return "Mayo";
            case "06":
                return "Junio";
            case "07":
                return "Julio";
            case "08":
                return "Agosto";
            case "09":
                return "Septiembre";
            case "10":
                return "Octubre";
            case "11":
                return "Noviembre";
            case "12":
                return "Diciembre";
            default:
                return "Indefinido";
        }
    }


    public String ParseToFechaFormato2(String fechaString){//año-mes-dia

        String[] vector = fechaString.split("-");
        String fecha = vector[2]+"-"+vector[1]+"-"+vector[0];
       return fecha;
    }

    public String ParseNumberWithPoints(String numero){

        numero = numero.split("\\.")[0];
        char[] aux = numero.toCharArray();
        String result = "";
        int cont = 0;

        for (int i = aux.length-1; i >= 0; i--) {
            result = aux[i] + result;
            if(++cont%3==0 && cont!=aux.length)
                result = "." + result;

        }
        return result;
    }

    public String convertirSinNotacionCientifica(double val){
        Locale.setDefault(Locale.US);
        DecimalFormat num = new DecimalFormat("#.###");
        return num.format(val);
    }

    public double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)* Math.pow(10, numeroDecimales);
        resultado= Math.round(resultado);
        resultado=(resultado/ Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
}
