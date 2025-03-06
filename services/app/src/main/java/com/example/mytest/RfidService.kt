package com.example.mytest

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.UUID

class RfidService : Service() {

    private val CHANNEL_ID = "RFIDTestChannel"

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        // return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("RFIDSERVICE","onCreated)")
        createForegroundNotification();
    }

    private fun createForegroundNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("RFID Service")
            .setContentText("Connessione a RFID in corso...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("RFIDSERVICE","onCreated)")
        initAndStartRfidReader()
        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY;

    }

    @SuppressLint("MissingPermission")
    private fun initAndStartRfidReader() {
        //inizializziamo la connessione
        val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter != null && btAdapter.isEnabled) {
            val device : BluetoothDevice = btAdapter.getRemoteDevice("MAC_ADDRESS");

            val socket = device.createRfcommSocketToServiceRecord(UUID.fromString("UUID"))
            socket.connect()

            val inputStream = socket.inputStream
            val outputStream = socket.outputStream

            val buffer = ByteArray(1024);
            while (true) {
                val byteRead = inputStream.read(buffer)
                if (byteRead != -1) {
                    val rfidData = String(buffer, 0, byteRead)
                    Log.d("RFIDDATA", "RFID tag letto : $rfidData")
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}