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


//        PieChart chart = findViewById(R.id.chart);
//
//        List<PieEntry> entries = new ArrayList<>();
//
//        entries.add(new PieEntry(counts.getWcount(), "Working"));
//        entries.add(new PieEntry(counts.getEcount(), "Faulty"));
//        entries.add(new PieEntry(counts.getCcount(), "Grounded"));
//        // entries.add(new PieEntry(30.8f, "Blue"));
//
//        PieDataSet set = new PieDataSet(entries, "Assets");
//        PieData data = new PieData(set);
//        int colors[] = {R.color.colorPrimary, R.color.red, R.color.orange_color_picker};
//        set.setColors(colors, this);
//        chart.setData(data);
//
//        Description description = new Description();
//        description.setText("Assets Summary");
//        chart.setCenterText("All " + counts.getAcount());
//        chart.setDescription(description);
//        chart.invalidate(); // refresh
//
        initIssues(counts);
        initAssets(counts);
        initCust(counts);
        initEng(counts);


    }

    private void initAssets(Counts counts) {


        PieChart chart = findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(counts.getWcount(), "Working"));
        entries.add(new PieEntry(counts.getFcount(), "Faulty"));
        entries.add(new PieEntry(counts.getGcount(), "Grounded"));
        // entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Assets " + counts.getAcount());
        PieData data = new PieData(set);
        int colors[] = {R.color.colorPrimary, R.color.red, R.color.orange_color_picker};
        set.setColors(colors, this);
        chart.setData(data);

        Description description = new Description();
        description.setText("Assets Summary");
        chart.setCenterText("All " + counts.getAcount());
        chart.setDescription(description);
        chart.invalidate(); // refresh


    }

    private void initIssues(Counts counts) {


        PieChart chart = findViewById(R.id.chartIssues);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(counts.getOissues(), "Open"));
        entries.add(new PieEntry(counts.getPissues(), "In Progress"));
        entries.add(new PieEntry(counts.getCissues(), "CLosed"));
        // entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Issues");
        PieData data = new PieData(set);
        int colors[] = {R.color.colorPrimary, R.color.red, R.color.orange_color_picker};
        set.setColors(colors, this);
        chart.setData(data);

        Description description = new Description();
        description.setText("Issues Summary");
        chart.setCenterText("Issues ");
        chart.setDescription(description);
        chart.setEntryLabelTextSize(13.5f);
        chart.invalidate(); // refresh


    }

    private void initEng(Counts counts) {


        PieChart chart = findViewById(R.id.chartEng);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(counts.getEcount(), "Engineers"));
//        entries.add(new PieEntry(counts.getEcount(), "Faulty"));
//        entries.add(new PieEntry(counts.getCcount(), "Grounded"));
//        // entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Engineers " + counts.getEcount());
        PieData data = new PieData(set);
        int colors[] = {R.color.colorPrimary, R.color.red, R.color.orange_color_picker};
        set.setColors(colors, this);
        chart.setData(data);

        Description description = new Description();
        description.setText("Engineers Summary");
        chart.setCenterText("All " + +counts.getEcount());
        chart.setDescription(description);
        chart.invalidate(); // refresh


    }

    private void initCust(Counts counts) {


        PieChart chart = findViewById(R.id.chartCust);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(counts.getCcount(), "Customers"));
//        entries.add(new PieEntry(counts.getEcount(), "Faulty"));
//        entries.add(new PieEntry(counts.getCcount(), "Grounded"));
//        // entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "CLients " + counts.getCcount());
        PieData data = new PieData(set);
        int colors[] = {R.color.colorPrimary, R.color.red, R.color.orange_color_picker};
        set.setColors(colors, this);
        chart.setData(data);

        Description description = new Description();
        description.setText("Clients Summary");
        chart.setCenterText("All " + counts.getCcount());
        chart.setDescription(description);
        chart.invalidate(); // refresh


    }

}
