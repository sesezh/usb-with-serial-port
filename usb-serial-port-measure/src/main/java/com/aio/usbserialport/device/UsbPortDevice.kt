package com.aio.usbserialport.device

import android.content.Context
import com.aio.usbserialport.parser.DataPackageEntity
import com.aio.usbserialport.parser.Parser
import com.hd.serialport.listener.UsbMeasureListener
import com.hd.serialport.method.DeviceMeasureController
import com.hd.serialport.param.UsbMeasureParameter
import com.hd.serialport.usb_driver.UsbSerialPort
import java.util.*

/**
 * Created by hd on 2017/8/28 .
 *
 */
open class UsbPortDevice(context: Context,aioDeviceType:Int, val port: UsbSerialPort? = null,val parameter: UsbMeasureParameter, parser: Parser)
    : Device(context,aioDeviceType, parser), UsbMeasureListener {

    override fun write(usbSerialPort: UsbSerialPort) {
        usbSerialPortList.add(usbSerialPort)
    }

    override fun measure() {
        DeviceMeasureController.measure(usbSerialPort = port, usbMeasureParameter = parameter, usbMeasureListener = this)
    }

    override fun release() {
    }

    override fun measureError(message: String) {
        error(message)
    }

    override fun measuring(usbSerialPort: UsbSerialPort, data: ByteArray) {
        dataQueue.put(DataPackageEntity(port = usbSerialPort, data = data))
        addLogcat(Arrays.toString(data))
    }

}