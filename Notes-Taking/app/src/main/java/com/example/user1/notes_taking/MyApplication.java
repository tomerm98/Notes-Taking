package com.example.user1.notes_taking;

import com.parse.Parse;

/**
 * Created by Tomer on 16/10/2016.
 */

public class MyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //This will only be called once in your app's entire lifecycle.
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getResources().getString(R.string.PARSE_APPLICATION_ID))
                .clientKey(getResources().getString(R.string.PARSE_CLIENT_KEY))
                .server(getResources().getString(R.string.PARSE_SERVER_ADDRESS)).build()
        );
    }
}
