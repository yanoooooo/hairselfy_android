package project.hasebe.yano.hairselfy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class inputUrlActivity extends AppCompatActivity {
    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_url);

        globals = (Globals)this.getApplication();
        globals.init();
        globals.bleUtil.serchDevice();

        //text
        final EditText ip_edit = (EditText)findViewById(R.id.ip_edit);
        final EditText id_edit = (EditText)findViewById(R.id.id_edit);

        //start button
        Button startButton = (Button)findViewById(R.id.start_button);
        Button bleButton = (Button)findViewById(R.id.ble_button);
        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Sub 画面を起動
                Intent intent = new Intent(inputUrlActivity.this, WebActivity.class);
                intent.putExtra("ip", "192.168.108.239");
                //intent.putExtra("ip", ip_edit.getText().toString());
                intent.putExtra("id", id_edit.getText().toString());
                startActivity(intent);
            }
        });

        bleButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                globals.bleUtil.connect();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        globals.bleUtil.pause();
    }
}
