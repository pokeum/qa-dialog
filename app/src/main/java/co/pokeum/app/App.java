package co.pokeum.app;

import android.app.Application;

import co.ab180.airbridge.Airbridge;
import co.ab180.airbridge.AirbridgeLogLevel;
import co.ab180.airbridge.AirbridgeOption;
import co.ab180.airbridge.AirbridgeOptionBuilder;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AirbridgeOption config = new AirbridgeOptionBuilder("YOUR_APP_NAME", "YOUR_APP_SDK_TOKEN")
                .setLogLevel(AirbridgeLogLevel.DEBUG)
                .build();
        Airbridge.initializeSDK(this, config);
    }
}