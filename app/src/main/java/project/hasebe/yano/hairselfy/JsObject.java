package project.hasebe.yano.hairselfy;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by aynishim on 2016/09/18.
 */
public class JsObject {
    private static final String TAG = "JsObject";
    Globals globals;

    JsObject(Globals glb) {
        globals = glb;
        globals.bleUtil.write("0");
    }

    @JavascriptInterface
    public void rotServo(String rot) {
        Log.i(TAG, "calling js: " + rot.toString());
        globals.bleUtil.write(rot);
    }
}
