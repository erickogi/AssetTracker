package com.assettrack.assettrack.Views.Admin.Dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.Parsers.CountsParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.Counts;
import com.assettrack.assettrack.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashBoardAdmin extends AppCompatActivity {
    // SlimChart slimChart;
    private ProgressDialog progressDialog;
    private PrefManager prefManager;
    private Counts counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getCounts();


//        slimChart = findViewById(R.id.slimChart);
//
//        SlimChart slimChart = findViewById(R.id.slimChart);
//
////        item1.setColor(resources.getColor(R.color.colorPrimary))
////        item2.setColor(resources.getColor(R.color.green_color_picker))
////        item3.setColor(resources.getColor(R.color.red))
////        item4.setColor(resources.getColor(R.color.orange_color_picker))
//
//        //Optional - create colors array
//        int[] colors = new int[4];
//        //795548
//        colors[0] = getResources().getColor(R.color.colorPrimary);
//        colors[1] = getResources().getColor(R.color.green_color_picker);
//        colors[2] = getResources().getColor(R.color.red);
//        colors[3] = getResources().getColor(R.color.orange_color_picker);
//       // colors[4] = Color.rgb(247, 76, 110);
//        slimChart.setColors(colors);
//
//        //Create array for your stats
//        final float[] stats = new float[4];
//        stats[0] = 100;
//        stats[1] = 85;
//        stats[2] = 40;
//        stats[3] = 25;
//        slimChart.setStats(stats);
//
//        //Play animation
//        slimChart.setStartAnimationDuration(2000);
//
//        //Set single color - other colors will be generated automatically
//        slimChart.setColor(ContextCompat.getColor(this, R.color.green_color_picker));
//
//        slimChart.setStrokeWidth(13);
//        slimChart.setText("234");
//        //slimChart.setTextColor(this.getResources().getColor(R.color.black));
//        slimChart.setRoundEdges(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.hide();
    }

    private Counts getCounts() {

        prefManager = new PrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("working....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = APiConstants.Companion.getGlobalCount();


        // Log.d("getData",url+"\n"+params.toString()+"\n"+prefManager.getToken());
        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    // progressDialog.setMessage(error.getMessage());
                    progressDialog.dismiss();
                }
                initUI(new Counts());

                Log.d("getData", error.getErrorBody());
            }

            @Override
            public void onError(@NotNull String error) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    // progressDialog.setMessage(error);
                    progressDialog.dismiss();
                }
                initUI(new Counts());
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {

                Log.d("getData", response);
                if (progressDialog != null && progressDialog.isShowing()) {
                    // progressDialog.setMessage(error);
                    progressDialog.dismiss();
                }
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    counts = CountsParser.parse(jsonObject);
                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
                }

                initUI(counts);
            }
        });


        return counts;
    }

    private void initUI(Counts counts) {


        PieChart chart = findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(counts.getWcount(), "Working"));
        entries.add(new PieEntry(counts.getEcount(), "Faulty"));
        entries.add(new PieEntry(counts.getCcount(), "Grounded"));
        // entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Assets");
        PieData data = new PieData(set);
        int colors[] = {R.color.colorPrimary, R.color.red, R.color.orange_color_picker};
        set.setColors(colors, this);
        chart.setData(data);

        Description description = new Description();
        description.setText("Assets Summary");
        chart.setCenterText("All " + counts.getAcount());
        chart.setDescription(description);
        chart.invalidate(); // refresh


//
//
//
//
//        Pie pie = AnyChart.pie();
//        Log.d("getData", "found "+counts.getAcount());
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("Working", counts.getWcount()));
//        data.add(new ValueDataEntry("Faulty", counts.getFcount()));
//        data.add(new ValueDataEntry("Grounded", counts.getGcount()));
//
//        pie.setData(data);
//
//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//        anyChartView.setChart(pie);
//
//
//
//
//        Pie pieIssues = AnyChart.pie();
//        Log.d("getData", "found "+counts.getAcount());
//
//        List<DataEntry> datai = new ArrayList<>();
//        datai.add(new ValueDataEntry("Open", counts.getOissues()));
//        datai.add(new ValueDataEntry("In Progress", counts.getPissues()));
//        datai.add(new ValueDataEntry("Closed", counts.getCissues()));
//
//        pieIssues.setData(datai);
//
//        AnyChartView anyChartViewi = findViewById(R.id.any_chart_issues);
//        anyChartViewi.setChart(pieIssues);
//
//
//        Pie pieUsers = AnyChart.pie();
//        Log.d("getData", "found "+counts.getAcount());
//
//        List<DataEntry> datausers = new ArrayList<>();
//        datausers.add(new ValueDataEntry("Engineers", counts.getEcount()));
//        datausers.add(new ValueDataEntry("Clients", counts.getCcount()));
//       // datausers.add(new ValueDataEntry("Closed", counts.getCissues()));
//
//        pieUsers.setData(datausers);
//
//        AnyChartView anyChartViewusers = findViewById(R.id.any_chart_users);
//        anyChartViewusers.setChart(pieUsers);
    }

}
