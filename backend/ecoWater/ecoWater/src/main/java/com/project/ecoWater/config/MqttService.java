package com.project.ecoWater.config;

import com.project.ecoWater.consumption.app.WaterConsumptionAppService;
import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.device.app.DeviceApplicationService;
import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.infrastructure.DeviceMapper;
import com.project.ecoWater.filling.app.TankFillingAppService;
import com.project.ecoWater.filling.domain.TankFilling;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class MqttService implements MqttCallback {
    private static final String BROKER = "tcp://broker.emqx.io:1883";
    private static final String CLIENT_ID = "SpringBootBackend_" + UUID.randomUUID();
    private static final String WATER_TOPIC = "sensor/agua";
    private static final String HEARTBEAT_TOPIC = "sensor/pulso";
    private final Map<String, Long> lastProcessingTimes = new ConcurrentHashMap<>();
    private Map<Long, Float> lastTankLevels = new HashMap<>();
    Map<Long, Integer> sameLevelCount = new HashMap<>();
    private Map<Long, TankFilling> activeTankFillings = new HashMap<>();
    private static final long PROCESSING_INTERVAL = 5 * 1000;

    private MqttClient client;
    private final SensorDataAppService sensorDataAppService;
    private final DeviceApplicationService deviceApplicationService;
    private final WaterConsumptionAppService waterConsumptionAppService;
    private final WaterTankLevelAppService waterTankLevelAppService;
    private final TankFillingAppService tankFillingAppService;


    MqttService(
            SensorDataAppService sensorDataAppService,
            DeviceApplicationService deviceApplicationService,
            WaterConsumptionAppService waterConsumptionAppService,
            WaterTankLevelAppService waterTankLevelAppService,
            TankFillingAppService tankFillingAppService
            ) {
        this.sensorDataAppService = sensorDataAppService;
        this.deviceApplicationService = deviceApplicationService;
        this.waterConsumptionAppService = waterConsumptionAppService;
        this.waterTankLevelAppService = waterTankLevelAppService;
        this.tankFillingAppService = tankFillingAppService;
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
            client.subscribe(WATER_TOPIC);
            client.subscribe(HEARTBEAT_TOPIC);
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
            if (HEARTBEAT_TOPIC.equals(topic)) {
                processHeartbeatMessage(json);
            } else if (WATER_TOPIC.equals(topic)) {
                processWaterSensorMessage(json);
            }

        } catch (Exception e) {
            System.err.println("❌ Error procesando mensaje MQTT.");
            e.printStackTrace();
        }
    }

    private void processHeartbeatMessage(String json) throws Exception {
        JSONObject obj = new JSONObject(json);
        String deviceId = obj.getString("deviceId");

        Optional<Device> deviceOpt = deviceApplicationService.getDevice(deviceId);
        if (deviceOpt.isEmpty()) {
            System.err.println("❌ Dispositivo no registrado: " + deviceId);
            return;
        }

        Device device = deviceOpt.get();
        String email = device.getUser().getEmail();
        device.setLastSeen(Timestamp.valueOf(LocalDateTime.now()));
        device.setConnected(true);

        deviceApplicationService.updateStatus(device,email);

        System.out.println("❤️ Pulso de vida actualizado para dispositivo: " + deviceId);
    }

    private void processWaterSensorMessage(String json)throws Exception{
        JSONObject obj = new JSONObject(json);
        String deviceId = obj.getString("deviceId");

        long currentTime = System.currentTimeMillis();
        Long lastTime = lastProcessingTimes.get(deviceId);

        if (lastTime != null && (currentTime - lastTime) < PROCESSING_INTERVAL) {
            System.out.println("⏳ Datos ignorados (frecuencia muy alta) para: " + deviceId);
            return;
        }
        lastProcessingTimes.put(deviceId, currentTime);

        System.out.println("🔍 Dispositivo: " + deviceId);
        System.out.println("🔍 Buscando dispositivo con ID: " + deviceId);

        Optional<Device> deviceOpt = deviceApplicationService.getDevice(deviceId);

        if (deviceOpt.isEmpty()) {
            System.err.println("❌ Dispositivo no registrado: " + deviceId);
            return;
        }

        Device device = deviceOpt.get();
        String email = device.getUser().getEmail();
        System.out.println("✅ Dispositivo encontrado. Tipo: " + device.getDeviceType() + ", Usuario: " + email);

        if (obj.has("distance")) {
            // --- Sensor de proximidad ---
            float distance = obj.getFloat("distance");
            System.out.println("📡 Procesando sensor de proximidad. Distancia: " + distance + " m");

            SensorData sd = SensorData.builder()
                    .device(DeviceMapper.deviceToDeviceDTO(device))
                    .distance(distance)
                    .build();
            SensorData saved = sensorDataAppService.createSensorData(sd, email);

            if (saved != null && device.getTank() != null) {
                WaterTankLevel wl = WaterTankLevel.builder()
                        .tank(device.getTank())
                        .build();
                WaterTankLevel savedLevel=waterTankLevelAppService.save(wl, email);
                savingFilling(savedLevel, device, email);

            }

        } else if (obj.has("flowRate")) {
            float flowRate = obj.getFloat("flowRate");
            float totalVolume = obj.getFloat("totalVolume");

            if (flowRate == 0 || totalVolume == 0) {
                System.out.println("⚠️ Consumo cero detectado. No se guardará.");
                return;
            }

            System.out.println("💧 Procesando caudalímetro. Flujo: " + flowRate + " L/min, Volumen: " + totalVolume + " L");

            WaterConsumption wc = WaterConsumption.builder()
                    .device(DeviceMapper.deviceToDeviceDTO(device))
                    .flowRate(flowRate)
                    .totalConsumption(totalVolume)
                    .build();
            waterConsumptionAppService.create(wc, email);

        } else {
            System.err.println("⚠️ Formato de mensaje no reconocido para dispositivo: " + deviceId);
        }
    }

    private void savingFilling(WaterTankLevel savedLevel, Device device, String email){
        Long tankId = device.getTank().getTankId();
        float currentLevel = savedLevel.getWaterLevel();

        Float lastLevel = lastTankLevels.get(tankId);

        if (lastLevel != null && currentLevel > lastLevel && !activeTankFillings.containsKey(tankId)) {
            TankFilling filling = TankFilling.builder()
                    .tank(device.getTank())
                    .build();
            TankFilling savedFilling = tankFillingAppService.save(filling, email);
            activeTankFillings.put(tankId, savedFilling);
            System.out.println("🚰 Iniciado llenado para tanque ID " + tankId);
        }

        lastTankLevels.put(tankId, currentLevel);
        final int SAME_LEVEL_THRESHOLD = 3;

        if (activeTankFillings.containsKey(tankId)) {
            if (lastLevel != null && currentLevel == lastLevel) {
                int count = sameLevelCount.getOrDefault(tankId, 0) + 1;
                sameLevelCount.put(tankId, count);

                if (count >= SAME_LEVEL_THRESHOLD) {
                    TankFilling activeFilling = activeTankFillings.get(tankId);
                    tankFillingAppService.confirmTankFull(activeFilling.getFillingId(), email);
                    activeTankFillings.remove(tankId);
                    sameLevelCount.remove(tankId);
                    System.out.println("✅ Llenado finalizado para tanque ID " + tankId);
                }
            } else {
                sameLevelCount.put(tankId, 0);
            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("📦 Entrega MQTT completada: " + iMqttDeliveryToken);

    }
}
