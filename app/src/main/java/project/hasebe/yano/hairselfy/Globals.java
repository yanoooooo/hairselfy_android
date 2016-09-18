package project.hasebe.yano.hairselfy;

import android.app.Application;

/**
 * Created by aynishim on 2016/09/18.
 */
public class Globals extends Application {
    BluetoothUtil bleUtil;

    public void init() {
        bleUtil = new BluetoothUtil();
    }
}
