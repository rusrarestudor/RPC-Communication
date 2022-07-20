package distributedsystems.a1.RPC.measurementService;

import distributedsystems.a1.RPC.measurementService.MeasurementException;
import distributedsystems.a1.RPC.measurementService.MeasurementServer;

import java.util.ArrayList;

public interface MeasurementServiceServer {

    ArrayList<MeasurementServer> sendMeasurements(Long deviceID, int days) throws MeasurementException;
    ArrayList<MeasurementServer> sendInterval(Long deviceID, int d) throws MeasurementException;
    String[] deviceRecognition();

}
