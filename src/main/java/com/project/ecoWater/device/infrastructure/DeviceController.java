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
        Device createdDevice = deviceApplicationService.createDevice(device, userDetails.getUsername());
        return ResponseEntity.ok(createdDevice);
    }

    @GetMapping("/getDevice/{deviceId}")
    public ResponseEntity<Optional<Device>> getDevice(@PathVariable String deviceId) {
        Optional<Device> device = deviceApplicationService.getDevice(deviceId);
        return ResponseEntity.ok(device);
    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<List<Device>> getAllDevices(@AuthenticationPrincipal UserDetails userDetails) {
        List<Device> devices = deviceApplicationService.getAllDevices(userDetails.getUsername());
        return ResponseEntity.ok(devices);
    }
    @PatchMapping("/updateDevice")
    public ResponseEntity<Optional<Device>> updateDevice(@RequestBody Device device, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Device> updatedDevice = deviceApplicationService.updateDevice(device, userDetails.getUsername());
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/deleteDevice/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable String deviceId, @AuthenticationPrincipal UserDetails userDetails) {
        deviceApplicationService.deleteDevice(deviceId, userDetails.getUsername());
        return ResponseEntity.ok("Device deleted successfully");
    }

}
