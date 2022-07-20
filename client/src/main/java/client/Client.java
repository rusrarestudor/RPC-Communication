package client;

import charts.ChartBaseline;
import charts.ChartHistory;
import charts.ChartStartFeature;
import measurement.MeasurementException;
import measurement.MeasurementService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Client {

    @Deprecated
    @Bean
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/measurements");
        invoker.setServiceInterface(MeasurementService.class);
        return invoker;
    }

    public Measurement stringToMeasurement(String string){

        String segments[] = string.split(",");

        String hour = segments[0].split("=")[1];
        String value = segments[1].split("=")[1];
        String day = segments[2].split("=")[1].substring(0, segments[2].split("=")[1].length() - 1);

        Measurement measurement = new Measurement(value, day, hour);

        return measurement;
    }

    public static void main(String[] args) throws MeasurementException {

        ConfigurableApplicationContext context = SpringApplication.run(Client.class, args);
        MeasurementService measurementService = context.getBean(MeasurementService.class);

        Long deviceID = 1L;
        int days = 7;
        int d = 5;

        List<Measurement> measurementsList = measurementService.sendMeasurements(deviceID, days);

        ArrayList<Measurement> measurements = new ArrayList<>();
        for(int i = 0; i< measurementsList.size(); i++) {
            String aux = String.valueOf(measurementsList.get(i));
            String string = String.valueOf(aux);
            String[] segments = string.split(",");
            String hour = segments[0].split("=")[1];
            String value = segments[1].split("=")[1];
            String day = segments[2].split("=")[1].substring(0, segments[2].split("=")[1].length() - 1);

            Measurement measurement = new Measurement(value, day, hour);
            measurements.add(measurement);
        }


        //aici nu o sa mai generez Measurements deoarece e nevoie de acealsi measurements ca la baseline
        List<Measurement> measurementsList2 = measurementService.sendInterval(deviceID,d);
        System.out.println(measurementsList2);
        ArrayList<Measurement> measurements2 = new ArrayList<>();
        for(int i = 0; i< measurementsList2.size(); i++) {
            String aux = String.valueOf(measurementsList2.get(i));
            String string = String.valueOf(aux);
            String[] segments = string.split(",");
            String hour = segments[0].split("=")[1];
            String value = segments[1].split("=")[1];
            String day = segments[2].split("=")[1].substring(0, segments[2].split("=")[1].length() - 1);

            Measurement measurement = new Measurement(value, day, hour);
            measurements2.add(measurement);
        }

        System.setProperty("java.awt.headless", "false");

        ChartHistory chart = new ChartHistory(measurements);
        chart.setVisible(true);

        ChartBaseline chartBaseline = new ChartBaseline(measurements);
        chartBaseline.setVisible(true);

        ChartStartFeature chartStartFeature = new ChartStartFeature(measurements2);
        chartStartFeature.setVisible(true);

    }

}


