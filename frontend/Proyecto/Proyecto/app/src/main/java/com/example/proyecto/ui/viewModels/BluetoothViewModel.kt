package com.example.proyecto.ui.viewModels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.utils.BluetoothUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import android.provider.Settings

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _isDiscovering = MutableStateFlow(false)
    val isDiscovering: StateFlow<Boolean> = _isDiscovering

    private val _devices = mutableStateListOf<BluetoothDevice>()
    val devices: List<BluetoothDevice> = _devices

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private val discoveryTimeout = 12_000L
    private var discoveryJob: Job? = null



    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        if (_devices.none { d -> d.address == it.address }) {
                            _devices.add(it)
                            Log.d("DeviceViewModel", "FOUND: ${it.name} ${it.address}")
                        }
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("DeviceViewModel", "Discovery finished, reiniciando...")
                    _isDiscovering.value = false
                    discoveryJob?.cancel()
                    discoveryJob = viewModelScope.launch {
                        delay(3000)
                        startDiscoveryInternal()
                    }
                }
            }
        }
    }

    init {
        Log.d("DeviceViewModel", "BluetoothAdapter = $bluetoothAdapter")
        // 1) Registrar el receiver solo una vez
        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        app.registerReceiver(receiver, filter)
        Log.d("DeviceViewModel", "Receiver registrado en init")
    }
    @SuppressLint("MissingPermission")
    private fun startDiscoveryInternal() {
        bluetoothAdapter?.takeIf { it.isEnabled }?.let { adapter ->
            _isDiscovering.value = true
            _devices.clear()

            if (adapter.isDiscovering) adapter.cancelDiscovery()
            val started = adapter.startDiscovery()
            Log.d("DeviceViewModel", "startDiscoveryInternal: iniciado = $started")
        } ?: Log.e("DeviceViewModel", "startDiscoveryInternal: adaptador nulo o Bluetooth apagado")
    }

    @SuppressLint("MissingPermission")
    fun ensureBluetoothEnabled(activity: Activity) {
        when {
            bluetoothAdapter == null -> Log.e("DeviceViewModel", "NO HAY ADAPTADOR BLUETOOTH")
            !bluetoothAdapter.isEnabled -> {
                Log.d("DeviceViewModel", "Solicitando encendido Bluetooth")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                activity.startActivityForResult(intent, 1002)
            }
            else -> Log.d("DeviceViewModel", "Bluetooth ya está habilitado")
        }
    }

    @SuppressLint("ServiceCast")
    fun ensureLocationEnabled(activity: Activity) {
        val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("DeviceViewModel", "Solicitando activar GPS")
            activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else {
            Log.d("DeviceViewModel", "GPS ya activo")
        }
    }

    @SuppressLint("MissingPermission")
    fun startDiscovery(activity: Activity) {
        if (bluetoothAdapter == null) {
            Log.e("DeviceViewModel", "startDiscovery: No hay adaptador")
            return
        }
        if (!BluetoothUtils.hasPermissions(activity)) {
            Log.e("DeviceViewModel", "startDiscovery: Permisos insuficientes")
            return
        }
        if (!bluetoothAdapter.isEnabled) {
            Log.e("DeviceViewModel", "startDiscovery: Bluetooth apagado")
            return
        }
        // No limpiamos _devices para mantener estado dinámico
        startDiscoveryInternal()
        // Timeout
        discoveryJob?.cancel()
        discoveryJob = viewModelScope.launch {
            delay(discoveryTimeout)
            stopDiscovery()
        }
    }

    @SuppressLint("MissingPermission")
    fun stopDiscovery() {
        _isDiscovering.value = false
        discoveryJob?.cancel()
        bluetoothAdapter?.takeIf { it.isDiscovering }?.cancelDiscovery()
        Log.d("DeviceViewModel", "stopDiscovery: detenido")
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice, onConnected: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val socket = device.createRfcommSocketToServiceRecord(MY_UUID)
                bluetoothAdapter?.cancelDiscovery()
                socket.connect()
                bluetoothSocket = socket
                withContext(Dispatchers.Main) {
                    Log.i("DeviceViewModel", "Conectado a ${device.address}")
                    onConnected(true)
                }
            } catch (e: Exception) {
                Log.e("DeviceViewModel", "connectToDevice error", e)
                withContext(Dispatchers.Main) { onConnected(false) }
            }
        }
    }

    fun sendCredentials(ssid: String, password: String) {
        try {
            val msg = "${ssid.trim()},${password.trim()}\n"
            bluetoothSocket?.outputStream?.apply {
                write(msg.toByteArray())
                flush()
                Log.d("DeviceViewModel", "Credenciales enviadas")
            } ?: Log.e("DeviceViewModel", "sendCredentials: socket es null")
        } catch (e: Exception) {
            Log.e("DeviceViewModel", "sendCredentials error", e)
        }
    }

    companion object {
        private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    var selectedDevice: BluetoothDevice? = null
        internal set
}
