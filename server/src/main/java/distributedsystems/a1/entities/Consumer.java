package distributedsystems.a1.entities;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.DTO.TextMessageDTO;
import distributedsystems.a1.services.*;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Consumer {

    private final static String QUEUE_NAME = "hello";
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final DeviceService deviceService;
    private final UserService userService;
    private final WebSocketTextService webSocketTextService;

    @Autowired
    public Consumer(MeasurementService measurementService, SensorService sensorService, DeviceService deviceService, UserService userService, WebSocketTextService webSocketTextService1){

        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.deviceService = deviceService;
        this.userService = userService;
        this.webSocketTextService = webSocketTextService1;
    }

    @Scheduled(fixedRate = 10000)
    void listener() throws Exception {

        String url = System.getenv("CLOUDAMQP_URL");

        if(url == null){
            url = "amqps://xyldnjlt:SiWuOs5kznW0OXFXhGzIRsiyotVUETWd@goose.rmq2.cloudamqp.com/xyldnjlt";
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(url);
        //factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            try {
                JSONObject json = (JSONObject) parser.parse(message);

                LocalDateTime dateTime = LocalDateTime.parse(json.getAsString("timeStamp"));

                MeasurementDTO measurementDTO = new MeasurementDTO(dateTime,
                                                                    Double.parseDouble(json.getAsString("value")),
                                                                    json.getAsNumber("sensorID").longValue());

                System.out.println("    -> Received '" + message + "'");
                MeasurementDTO m1 = measurementService.findLast();
                measurementService.insertMeasurement(measurementDTO);


                //boolean peak = false;
                boolean peak =  (m1.getValue() - measurementDTO.getValue()) /
                                ChronoUnit.SECONDS.between(m1.getTimeStamp(), measurementDTO.getTimeStamp())
                            < sensorService.findSensorById(json.getAsNumber("sensorID").longValue()).getMax();


                TextMessageDTO textMessageDTO = new TextMessageDTO();
                String username = userService.findPersonById(
                                deviceService.findDeviceById(
                                sensorService.findSensorById(
                                        json.getAsNumber("sensorID").longValue()).getDeviceID()).getUserID()).toString();
                if(peak){
                    textMessageDTO.setMessage(" Max Value of the Sensor Exceeded! ");
                }else{
                    textMessageDTO.setMessage(json.getAsString("value") + " ");
                }
                webSocketTextService.sendMessage(textMessageDTO);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
