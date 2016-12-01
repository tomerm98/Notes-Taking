package com.example.user1.notes_taking;

import com.parse.Parse;

/**
 * Created by Tomer on 16/10/2016.
 */

public class MyApplication extends android.app.Application {
    final String PARSE_APPLICATION_ID = "5sdljXBc3gg1ppezz33JUTsW1MaSjisPHTv7P1wR";
    final String PARSE_CLIENT_KEY = "DK44MALuqw8JyZyBDx0KPv2nwxn32jhC1qBc0j0j";
    final String PARSE_SERVER_ADDRESS = "https://parseapi.back4app.com/";
    @Override
    public void onCreate() {
        super.onCreate();

        //This will only be called once in your app's entire lifecycle.
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APPLICATION_ID)
                .clientKey(PARSE_CLIENT_KEY)
                .server(PARSE_SERVER_ADDRESS).build()
        );
    }
}
