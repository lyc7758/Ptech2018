package polytechnantes.ptech2018;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiGraph extends AppCompatActivity {
    protected Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_graph);
        Intent intent = getIntent();
        String data = intent.getStringExtra(FetchingData.EXTRA_DATAFETCHED);
        updateFields(data);
        drawGraphs(findViewById(R.id.graph1));
        drawGraphs(findViewById(R.id.graph2));
        drawGraphs(findViewById(R.id.graph3));
        drawGraphs(findViewById(R.id.graph4));
        drawGraphs(findViewById(R.id.graph5));
        drawGraphs(findViewById(R.id.graph6));
        drawGraphs(findViewById(R.id.graph7));
        drawGraphs(findViewById(R.id.graph8));
    }

    private void updateFields(String jsonData){
        try{
            channel = new Channel("test", 8);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("feeds");
            Double[][] fieldvalue = new Double[channel.getFields().length][jsonArray.length()];
            String[] date = new String[jsonArray.length()];

                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonTemp = jsonArray.getJSONObject(j);
                    String entry_id = jsonTemp.getString("entry_id");
                    date[j] = jsonTemp.getString("created_at");
                    for(int i = 0;i<channel.getFields().length;i++) {
                       if( jsonTemp.getString(channel.getFieldNameByNb(i + 1)).equals("null")||jsonTemp.has(channel.getFieldNameByNb(i + 1))) {
                            fieldvalue[i][j] = null;
                       }
                       else{
                            fieldvalue[i][j] = jsonTemp.getDouble(channel.getFieldNameByNb(i + 1));
                       }
                    }
                }

            for(int i = 0; i<channel.getFields().length;i++){
                for(int j = 0;j<jsonArray.length();j++){
                        channel.addEntry(i, j, fieldvalue[i][j], date[j]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void drawGraphs(View view) {
        GraphView graph = (GraphView) view;
        graph.removeAllSeries();

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);


        if(view == findViewById(R.id.graph1) ) {
            LineGraphSeries<DataPoint> field1 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field1"),view));
            field1.setColor(Color.GREEN);
            graph.addSeries(field1);
        }

        if(view == findViewById(R.id.graph2)) {
            LineGraphSeries<DataPoint> field2 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field2"),view));
            field2.setColor(Color.BLUE);
            graph.addSeries(field2);
        }

        if(view == findViewById(R.id.graph3)) {
            LineGraphSeries<DataPoint> field3 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field3"),view));
            field3.setColor(Color.RED);
            graph.addSeries(field3);
        }
        if(view == findViewById(R.id.graph4)) {
            LineGraphSeries<DataPoint> field4 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field4"),view));
            field4.setColor(Color.RED);
            graph.addSeries(field4);
        }
        if(view == findViewById(R.id.graph5)) {
            LineGraphSeries<DataPoint> field5 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field5"),view));
            field5.setColor(Color.RED);
            graph.addSeries(field5);
        }
        if(view == findViewById(R.id.graph6)) {
            LineGraphSeries<DataPoint> field6 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field6"),view));
            field6.setColor(Color.RED);
            graph.addSeries(field6);
        }
        if(view == findViewById(R.id.graph7)) {
            LineGraphSeries<DataPoint> field7 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field7"),view));
            field7.setColor(Color.RED);
            graph.addSeries(field7);
        }
        if(view == findViewById(R.id.graph8)) {
            LineGraphSeries<DataPoint> field8 = new LineGraphSeries<>(fieldToDataPoint(channel.getFieldByName("field8"),view));
            field8.setColor(Color.RED);
            graph.addSeries(field8);
        }

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this,new SimpleDateFormat("dd/MM/yy HH:MM:ss")));
        graph.getGridLabelRenderer().setNumHorizontalLabels(2);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(0);
        //graph.getGridLabelRenderer().setHumanRounding(false);
    }



    public DataPoint[] fieldToDataPoint(Field field, View view) {
        Entry[] entryList = field.getEntries();
        int width = 0;
        for(Entry e : entryList) {
            if(e != null) width++;
        }
        DataPoint[] values = new DataPoint[width];

        int i = 0;
        for(Entry e : entryList) {
            if(e != null) {
                Date d = stringToDate(e.getEntryDate());
                values[i] = new DataPoint(d, e.getEntryValue());
                i++;
            }
        }
        if (i != 0)
        {
            GraphView graph = (GraphView) view;
            graph.getViewport().setMinX(values[0].getX());
            graph.getViewport().setMaxX(values[width-1].getX());
            graph.getViewport().setXAxisBoundsManual(true);
        }

        return values;
    }

    public Date stringToDate(String strDate) {
        Date d = new Date(Integer.valueOf(strDate.substring(0,4))-1900,Integer.valueOf(strDate.substring(5,7))-1,Integer.valueOf(strDate.substring(8,10)),Integer.valueOf(strDate.substring(11,13))+1,Integer.valueOf(strDate.substring(14,16)),Integer.valueOf(strDate.substring(17,19)));
        return d;
    }
}