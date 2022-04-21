package com.example.fineance.model;

public class Api {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private static final String ROOT_URL = "http://192.168.182.40/FineAnceApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createtransaction";
    public static final String URL_READ_HEROES = ROOT_URL + "gettransactions";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatetransaction";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletetransaction&id=";

}
