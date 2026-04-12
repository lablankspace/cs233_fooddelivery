package app.fooddelivery.service;

import org.json.JSONObject;
public class io {
    public static void write(String file, String data) {
       JSONObject jo = new JSONObject("{ \"abc\" : \"def\" }");
       System.out.println(jo);    }
}