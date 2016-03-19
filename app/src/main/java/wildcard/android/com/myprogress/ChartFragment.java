package wildcard.android.com.myprogress;


import android.graphics.Color;
import android.os.Bundle;
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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;
    private static final String TAG = "Chart Fragment";

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        setPieChartData(3, 100);
    }

    private void setPieChartData(int count, float range) {
        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) * mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        String[] mParties = {"Read", "Math", "Work", "Physics", "Informatics"};

        for (int i = 0; i < count + 1; i++) {
            xVals.add(mParties[i % mParties.length]);
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Task Accomplished");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        for (int c: ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }
        for (int c: ColorTemplate.COLORFUL_COLORS) {
            colors.add(c);
        }
        for (int c: ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }for (int c: ColorTemplate.PASTEL_COLORS) {
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
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
        String[] mMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
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
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        barChart.setData(data);
    }
}
