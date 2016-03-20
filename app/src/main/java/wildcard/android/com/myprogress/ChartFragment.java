package wildcard.android.com.myprogress;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;
    private SharedPreferences sharedPreferences;

    private ArrayList<String> tags;

    private static final String TAG = "Chart Fragment";

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        tags = MyUtils.getArr(sharedPreferences, "Tags");
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        setupUIs(v);
        return v;
    }


    public void setupUIs(View v) {
        pieChart = (PieChart) v.findViewById(R.id.piechart);
        barChart = (BarChart) v.findViewById(R.id.barchart);
        pieChartConfiguration();
        barChartConfiguration();
    }

    private void pieChartConfiguration() {
        pieChart.setUsePercentValues(false);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setRotationAngle(270);
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.getLegend().setEnabled(false);
        setPieChartData(4, 100);
    }

    private void setPieChartData(int count, float range) {
        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<>();

        ArrayList<String> xVals = new ArrayList<>();
        int[] nums = new int[5];

        String[] mParties = {"Work", "Study", "Social", "Travel", "Read"};

        for (int i = 0; i < tags.size(); i++) {
            switch (tags.get(i)) {
                case "work":
                    nums[0]++; break;
                case "study":
                    nums[1]++; break;
                case "social":
                    nums[2]++; break;
                case "travel":
                    nums[3]++; break;
                case "read":
                    nums[4]++; break;
                default:
                    nums[0]++; break;
            }
        }

        for (int i = 0; i < 5; i++) {
            yVals1.add(new Entry(nums[i], i));
        }

        for (int i = 0; i < 5; i++) {
            xVals.add(mParties[i]);
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Task Accomplished");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(6f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < mParties.length; i++) {
            colors.add(MyUtils.getLColor(mParties[i].toLowerCase()));
        }
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int)value);
            }
        });
        pieChart.setData(data);
        pieChart.highlightValue(null);
        pieChart.invalidate();
    }

    private void barChartConfiguration() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setDescription("");
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        barChart.disableScroll();
        barChart.setPinchZoom(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        setBarChartData(12, 50);
    }

    private void setBarChartData(int count, float range) {
        ArrayList<String> xVals = new ArrayList<>();
        String[] mMonths = {"Mar 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 12]);
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float mult = range + 1;
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setDrawValues(false);
        set1.setColor(Color.parseColor("#b3d465"));
        set1.setValueTextColor(Color.parseColor("#7e9d35"));
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        barChart.setData(data);
    }
}
