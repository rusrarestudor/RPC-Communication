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
import java.util.Comparator;

public class ChartHistory extends JFrame {

    ArrayList<Measurement> measurementsList;

    public ChartHistory(ArrayList<Measurement> measurementsList) {
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

        Comparator<Measurement> c = (s1, s2) -> s1.getDay().compareTo(s2.getDay());
        measurementsList.sort(c);

        XYSeriesCollection dataset = new XYSeriesCollection();

        int j = 0;
        int i = 1;

        XYSeries series = new XYSeries(measurementsList.get(j).getDay());
        series.add(Double.parseDouble(measurementsList.get(i).getHour()), Double.parseDouble(measurementsList.get(i).getValue()));

        while(measurementsList.size() > i){
            if(measurementsList.get(i).getDay().equals(measurementsList.get(j).getDay())){
                series.add(Double.parseDouble(measurementsList.get(i).getHour()), Double.parseDouble(measurementsList.get(i).getValue()));
                i++;
            }else{
                dataset.addSeries(series);
                series = new XYSeries(measurementsList.get(i).getDay());
                series.add(Double.parseDouble(measurementsList.get(i).getHour()), Double.parseDouble(measurementsList.get(i).getValue()));
                j = i;
                i++;
            }
        }

        dataset.addSeries(series);
        return dataset;
    }



    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Consume per day",
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
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesPaint(3, Color.yellow);
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));
        renderer.setSeriesPaint(4, Color.pink);
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));
        renderer.setSeriesPaint(5, Color.DARK_GRAY);
        renderer.setSeriesStroke(5, new BasicStroke(2.0f));
        renderer.setSeriesPaint(6, Color.magenta);
        renderer.setSeriesStroke(6, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Consume per day",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }



}