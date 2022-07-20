package distributedsystems.a1.RPC.server;

import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.RPC.measurementService.MeasurementException;
import distributedsystems.a1.RPC.measurementService.MeasurementServer;
import distributedsystems.a1.RPC.measurementService.MeasurementServiceServer;
import distributedsystems.a1.entities.Sensor;
import distributedsystems.a1.services.DeviceService;
import distributedsystems.a1.services.MeasurementService;
import distributedsystems.a1.services.SensorService;
import distributedsystems.a1.services.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementServiceServer {

    private final MeasurementService measurementService;
    private final SensorService sensorService;

    @Override
    public ArrayList<MeasurementServer> sendMeasurements(Long deviceID, int days) throws MeasurementException {


        measurementService.generateMeasurementForSensor(sensorService.findSensorIDByDevice(deviceID),days);
        List<MeasurementDTO> measurementListDTO = measurementService.findMeasurements();
        ArrayList<MeasurementServer> measurementsServer = new ArrayList<>();

        MeasurementDTO aux = new MeasurementDTO(null,0.0,null);

        for(int i = 0; i < measurementListDTO.size(); i++){

            aux = measurementListDTO.get(i);
            String value = Double.toString(aux.getValue());
            String date = aux.getTimeStamp().getDayOfMonth() + "." + aux.getTimeStamp().getMonthValue() + "." + aux.getTimeStamp().getYear();
            String hour = aux.getTimeStamp().getHour() + "";

            MeasurementServer measurementServer = new MeasurementServer(value, date, hour);
            measurementsServer.add(measurementServer);
        }
        System.out.println(measurementsServer);
        return measurementsServer;
    }

    @Override
    public ArrayList<MeasurementServer> sendInterval(Long deviceID, int d) throws MeasurementException{
        //aici nu o sa mai generez Measurements deoarece e nevoie de acealsi measurements ca la baseline
        //measurementService.generateMeasurementForSensor(sensorService.findSensorIDByDevice(deviceID),7);
        List<MeasurementDTO> measurementListDTO = measurementService.findMeasurements();
        ArrayList<MeasurementServer> measurementsServer = new ArrayList<>();

        Comparator<MeasurementDTO> c = (s1, s2) -> Integer.compare(s1.getTimeStamp().getDayOfMonth(), s2.getTimeStamp().getDayOfMonth());
        measurementListDTO.sort(c);

        double[] hoursArray = new double[25];
        Arrays.fill(hoursArray, 0);
        int h = 0;
        int poz = 0;
        while(measurementListDTO.size() > h){
            poz = Integer.valueOf(measurementListDTO.get(h).getTimeStamp().getHour());
            hoursArray[poz] +=measurementListDTO.get(h).getValue();
            h++;
        }

        for(int j = 0; j < hoursArray.length; j++){
            hoursArray[j] = (double) hoursArray[j] / 7;
        }


        int posOfFirstFromSmalestInterval = 0;
        double min = 9999L;
        for(int i = 0; i < measurementListDTO.size() - d; i++){
            double intervalSum = 0L;
            for(int j = i; j < d;j++){
                intervalSum += measurementListDTO.get(j).getValue();
            }
            if(intervalSum < min){
                min = intervalSum;
                posOfFirstFromSmalestInterval = i;
            }
        }


        double toAddToEachMeasurementOfInterval = 0L;

        MeasurementDTO aux = new MeasurementDTO(null,0.0,null);
        for(int k = posOfFirstFromSmalestInterval; k < (posOfFirstFromSmalestInterval + d); k++){

            aux = measurementListDTO.get(k);
            toAddToEachMeasurementOfInterval = sensorService.findSensorById(deviceID).getMax() + aux.getTimeStamp().getHour();
            String value = Double.toString(aux.getValue());
            String date = aux.getTimeStamp().getDayOfMonth() + "." + aux.getTimeStamp().getMonthValue() + "." + aux.getTimeStamp().getYear();
            String hour = aux.getTimeStamp().getHour() + "";

            MeasurementServer measurementServer = new MeasurementServer(value, date, hour);
            measurementsServer.add(measurementServer);
        }

        return measurementsServer;
    }

    @Override
    public String[] deviceRecognition(){
        String[] result = new String[2];
/*        List<UserDTO> userDTOS = userService.findUsers();
        result[0] = userDTOS.get(0).getUserName();
        result[1] = userDTOS.get(0).getPassword();*/
        return result;
    }
}