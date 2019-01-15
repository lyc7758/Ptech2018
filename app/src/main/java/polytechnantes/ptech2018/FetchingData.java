package polytechnantes.ptech2018;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchingData extends AppCompatActivity {
    protected String params[];
    public static final String EXTRA_DATAFETCHED = "fr.univ-nantes.polytech.DATAFETCHED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_data);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        params = intent.getStringExtra(MainActivity.EXTRA_PARAMS).split(";");

        // Capture the layout's TextView and set the string as its text
        TextView ipView = findViewById(R.id.ipView);
        ipView.setText("IP : "+params[0]);
        TextView channelView = findViewById(R.id.channelView);
        channelView.setText("Channel n°"+params[1]);
        receiveSetValues();
    }

    public void receiveSetValues() {
        String url = String.format("http://"+params[0]+"/channels/"+params[1]+"/feeds.json?results=8000&key=2J6TKLLGYWV9AUKW");

        new GetHttpValue().execute(url);
    }

    public void sendFetched(String value) {
        Intent intent = new Intent(this, MultiGraph.class);
        intent.putExtra(EXTRA_DATAFETCHED, value);
        startActivity(intent);
    }

    private class GetHttpValue extends AsyncTask<String, Void, String> {
        public GetHttpValue() {
        }

        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            HttpURLConnection urlConnection = null;
            String value = "UNDEFINED";
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                urlConnection.disconnect();

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Frag", "Error closing stream", e);
                    }
                }
            }
            return value;
        }

        @Override
        protected void onPostExecute(String value) {
            sendFetched(value);
        }
    }
}