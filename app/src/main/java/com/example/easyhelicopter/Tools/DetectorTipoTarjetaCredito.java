package com.example.easyhelicopter.Tools;

import com.example.easyhelicopter.Tools.TipoTarjetaCredito;

public class DetectorTipoTarjetaCredito {
    /// Visa comienza con 4. Los nuevos plásticos tienen 16 dígitos, los viejos tienen 13.
    private static final String VISA_REGEX = "^4[0-9]{12}(?:[0-9]{3})?$";

    /// MasterCard comienza con 51 hasta 55. Todos los plásticos tienen 16 dígitos.
    private static final String MASTER_CARD_REGEX = "^5[1-5][0-9]{14}$";

    /// American Express comienza con 34 o 37, y tienen hasta 15 dígitos.
    private static final String AMERICAN_EXPRESS_REGEX = "^3[47][0-9]{13}$";

    /// Diners Club comienzan con 300,305,36 o 38. Todas tienen 14 dígitos.
    /// Hay variantes que comienzan con 5 y deben ser procesadas como MasterCard
    private static final String DINERS_CLUB_REGEX = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";

    /// Dciscover comienza con 6011 o 65. Todas tienen 16 dígitos
    private static final String DISCOVER_REGEX = "^6(?:011|5[0-9]{2})[0-9]{12}$";

    /// JCB comienza con 2131 o 1800. Todas tienen 15 dígitos. Otras JCB que comienzan con 35, tienen 16 dígitos
    private static final String JCB_REGEX = "^(?:2131|1800|35\\d{3})\\d{11}$";

    /// Mensaje
    private static final String TIPO_DETECTADO = "Tipo detectado: %s";

    public static TipoTarjetaCredito detectarTipo(String numero, String cvv){
        /// Extraer la longitud
        int l = cvv.length();

        /// Para Mastercard, Visa, Discover y Dinners
        switch (l) {
            case 3:
                /// Los CVV de longitud 3 son para estos proveedores
                if(numero.matches(VISA_REGEX)){
                    return TipoTarjetaCredito.VISA;
                } else if(numero.matches(MASTER_CARD_REGEX)){
                    return TipoTarjetaCredito.MASTERCARD;
                } else if(numero.matches(DINERS_CLUB_REGEX)){
                    return TipoTarjetaCredito.DINNERS;
                } else if(numero.matches(DISCOVER_REGEX)){
                    return TipoTarjetaCredito.DISCOVER;
                } else if(numero.matches(JCB_REGEX)){
                    return TipoTarjetaCredito.JCB;
                } else {
                    return TipoTarjetaCredito.INVALIDO;
                }
            case 4:
                /// Los CVV de longitud 3 son para American Express
                if(numero.matches(AMERICAN_EXPRESS_REGEX)){
                    return TipoTarjetaCredito.AMEX;
                } else {
                    return TipoTarjetaCredito.INVALIDO;
                }
            default:
                return TipoTarjetaCredito.INVALIDO;
        }
    } /// Fin metodo detectarTipo

    public static TipoTarjetaCredito detectarTipo(String numero){

        /// Los CVV de longitud 3 son para estos proveedores
        if(numero.matches(VISA_REGEX)){
            return TipoTarjetaCredito.VISA;
        } else if(numero.matches(MASTER_CARD_REGEX)){
            return TipoTarjetaCredito.MASTERCARD;
        } else if(numero.matches(DINERS_CLUB_REGEX)){
            return TipoTarjetaCredito.DINNERS;
        } else if(numero.matches(DISCOVER_REGEX)){
            return TipoTarjetaCredito.DISCOVER;
        } else if(numero.matches(JCB_REGEX)){
            return TipoTarjetaCredito.JCB;
        } else if(numero.matches(AMERICAN_EXPRESS_REGEX)){
            return TipoTarjetaCredito.AMEX;
        } else {
            return TipoTarjetaCredito.INDETERMINADO;
        }
    } /// Fin metodo detectarTipo

}
