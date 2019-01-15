package polytechnantes.ptech2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_PARAMS = "fr.univ-nantes.polytech.PARAMETERS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getInitialParameters(View view) {
        Intent intent = new Intent(this, FetchingData.class);
        EditText editTextIP = (EditText) findViewById(R.id.editTextIP);
        EditText editTextChan = (EditText) findViewById(R.id.editTextChannel);
        String message = editTextIP.getText().toString() + ";" + editTextChan.getText().toString();
        intent.putExtra(EXTRA_PARAMS, message);
        startActivity(intent);
    }
}
