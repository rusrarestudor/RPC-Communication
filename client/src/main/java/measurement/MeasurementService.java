package measurement;

import client.Measurement;

import java.util.ArrayList;

public interface MeasurementService {
    ArrayList<Measurement> sendMeasurements(Long deviceID, int days) throws MeasurementException;
    ArrayList<Measurement> sendInterval(Long deviceID, int d) throws MeasurementException;

    String[] login();
}
