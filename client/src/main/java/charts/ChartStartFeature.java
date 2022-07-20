package charts;

import client.Measurement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ChartStartFeature extends JFrame {

    ArrayList<Measurement> measurementsList;

    public ChartStartFeature(ArrayList<Measurement> measurementsList) {
        this.measurementsList = measurementsList;
        initUI();
    }


    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
    }



    private XYDataset createDataset() {

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Start Time Feature");

        Comparator<Measurement> c = (s1, s2) -> s1.getHour().compareTo(s2.getHour());
        measurementsList.sort(c);

        double[] hoursArray = new double[25];
        Arrays.fill(hoursArray, 0);
        int i = 0;
        int poz = 0;
        while(measurementsList.size() > i){
            poz = Integer.valueOf(measurementsList.get(i).getHour());
            hoursArray[poz] = Double.parseDouble(measurementsList.get(i).getValue());
            i++;
        }

        for(int j = 0; j < hoursArray.length; j++){
            //if(hoursArray[i] != 0)
            series1.add(j, hoursArray[j]);
        }

        dataset.addSeries(series1);
        return dataset;
    }



    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Average Consumption",
                "Hours",
                "Values",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Start Time Feature",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }

}
