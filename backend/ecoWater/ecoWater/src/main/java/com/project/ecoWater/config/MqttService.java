package com.project.ecoWater.config;

import com.project.ecoWater.consumption.app.WaterConsumptionAppService;
import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.device.app.DeviceApplicationService;
import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.infrastructure.DeviceMapper;
import com.project.ecoWater.level.app.WaterTankLevelAppService;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.notification.alert.MonitoringService;
import com.project.ecoWater.sensor.app.SensorDataAppService;
import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MqttService implements MqttCallback {
    private static final String BROKER = "tcp://broker.emqx.io:1883";
    private static final String CLIENT_ID = "SpringBootBackend_" + UUID.randomUUID();
    private static final String TOPIC = "sensor/agua";

    private MqttClient client;
    private final SensorDataAppService sensorDataAppService;
    private final DeviceApplicationService deviceApplicationService;
    private final WaterConsumptionAppService waterConsumptionAppService;
    private final WaterTankLevelAppService waterTankLevelAppService;


    MqttService(
            SensorDataAppService sensorDataAppService,
            DeviceApplicationService deviceApplicationService,
            WaterConsumptionAppService waterConsumptionAppService,
            WaterTankLevelAppService waterTankLevelAppService
            ) {
        this.sensorDataAppService = sensorDataAppService;
        this.deviceApplicationService = deviceApplicationService;
        this.waterConsumptionAppService = waterConsumptionAppService;
        this.waterTankLevelAppService = waterTankLevelAppService;
    }

    @PostConstruct
    public void init() {
        connectAndSubscribe();
    }

    private void connectAndSubscribe() {
        try {
            client = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.setCallback(this);
            client.connect(options);
            client.subscribe(TOPIC);
            System.out.println("✅ Conectado y suscrito a MQTT.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.err.println("⚠️ Conexión MQTT perdida. Intentando reconectar...");
        throwable.printStackTrace();
        connectAndSubscribe();

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("📨 Mensaje recibido en tópico [" + topic + "]: " + mqttMessage);

        try {
            String json = new String(mqttMessage.getPayload());
            System.out.println("🧾 Payload recibido: " + json);
            JSONObject obj = new JSONObject(json);
            String deviceId = obj.getString("deviceId");
            float value = obj.getFloat("distance");
            System.out.println("🔍 Dispositivo: " + deviceId);
            System.out.println("🔍 Valor: " + value);
            System.out.println("🔍 Buscando dispositivo con ID: " + deviceId);
            Optional<Device> deviceOpt = deviceApplicationService.getDevice(deviceId);

            if (deviceOpt.isEmpty()) {
                System.err.println("❌ Dispositivo no registrado: " + deviceId);
                return;
            }

            Device device = deviceOpt.get();
            String email = device.getUser().getEmail();
            System.out.println("✅ Dispositivo encontrado. Tipo: " + device.getDeviceType() + ", Usuario: " + email);

            switch (device.getDeviceType()) {
                case CAUDALIMETRO -> {
                    System.out.println("💧 Procesando caudalímetro con valor: " + value);
                    WaterConsumption wc = WaterConsumption.builder()
                            .device(DeviceMapper.deviceToDeviceDTO(device))
                            .flowRate(value)
                            .build();
                    WaterConsumption saved = waterConsumptionAppService.create(wc, email);
                    System.out.println("💧 Consumo: " + wc);
                    System.out.println("💧 Consumo guardado: " + saved);
                    System.out.println("✅ Caudal registrado correctamente.");
                }
                case SENSOR_PROXIMIDAD -> {
                    System.out.println("📡 Procesando sensor de proximidad con valor: " + value);
                    SensorData sd = SensorData.builder()
                            .device(DeviceMapper.deviceToDeviceDTO(device))
                            .distance(value)
                            .build();
                    SensorData saved = sensorDataAppService.createSensorData(sd, email);
                    System.out.println("💧 Dato: " + sd);
                    System.out.println("💧 Dato guardado : " + saved);
                    if(saved != null) {
                        WaterTankLevel wl = WaterTankLevel.builder()
                                .tank(device.getTank())
                                .build();
                        WaterTankLevel savedLevel= waterTankLevelAppService.save(wl, email);

                        System.out.println("🗄️ Nivel del tanque procesado para el sensor.");
                    }
                    System.out.println("✅ Sensor de proximidad registrado correctamente.");

                }
                default -> {
                    System.err.println("⚠️ Tipo de dispositivo desconocido.");
                }
            }

            System.out.println("✅ Datos guardados de " + device.getDeviceType() + " para: " + deviceId);



        } catch (Exception e) {
            System.err.println("❌ Error procesando mensaje MQTT.");
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("📦 Entrega MQTT completada: " + iMqttDeliveryToken);

    }
}
