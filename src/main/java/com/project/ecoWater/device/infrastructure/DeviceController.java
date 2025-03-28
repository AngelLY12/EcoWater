package com.project.ecoWater.device.infrastructure;


import com.project.ecoWater.device.app.DeviceApplicationService;
import com.project.ecoWater.device.domain.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor()
public class DeviceController {
    private final DeviceApplicationService deviceApplicationService;

    @PostMapping("/addDevice")
    public ResponseEntity<Device> createDevice(@RequestBody DeviceRequest device, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Email recibido en controlador: " + userDetails.getUsername());

        try {

            Device createdDevice = deviceApplicationService.createDevice(device, userDetails.getUsername());
            return ResponseEntity.ok(createdDevice);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getDevice/{deviceId}")
    public ResponseEntity<Optional<Device>> getDevice(@PathVariable UUID deviceId) {
        try {
            Optional<Device> device = deviceApplicationService.getDevice(deviceId);
            return ResponseEntity.ok(device);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<List<Device>> getAllDevices() {
        try {
            List<Device> devices = deviceApplicationService.getAllDevices();
            return ResponseEntity.ok(devices);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @PatchMapping("/updateDevice")
    public ResponseEntity<Optional<Device>> updateDevice(@RequestBody Device device, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Optional<Device> updatedDevice = deviceApplicationService.updateDevice(device, userDetails.getUsername());
            return ResponseEntity.ok(updatedDevice);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deleteDevice/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable UUID deviceId) {
        try {
            deviceApplicationService.deleteDevice(deviceId);
            return ResponseEntity.ok("Device deleted successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
