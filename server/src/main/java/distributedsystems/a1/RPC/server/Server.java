package distributedsystems.a1.RPC.server;

import distributedsystems.a1.RPC.measurementService.MeasurementServiceServer;
import distributedsystems.a1.services.MeasurementService;
import distributedsystems.a1.services.SensorService;
import distributedsystems.a1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@RequiredArgsConstructor
public class Server {

    private  final MeasurementService measurementService;
    private  final SensorService sensorService;
    @Bean
    MeasurementServiceServer measurementServiceServer() {
        return new MeasurementServiceImpl(measurementService, sensorService);
    }

    @Bean(name = "/measurements")
    @Deprecated
    RemoteExporter mService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(measurementServiceServer());
        exporter.setServiceInterface(MeasurementServiceServer.class);
        return exporter;
    }


}