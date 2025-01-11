import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class PressureSensor {
    
    public static void main(String[] args) throws InterruptedException {
        
        // Create GPIO controller
        final GpioController gpio = GpioFactory.getInstance();
        
        // Set the pin for the analog sensor
        final Pin analogSensorPin = RaspiPin.GPIO_26;
        
        // Create analog input pin
        final GpioPinAnalogInput analogSensor = gpio.provisionAnalogInputPin(analogSensorPin);
        
        // Read analog sensor value
        while(true) {
            double sensorValue = analogSensor.getValue();
            System.out.println("Analog Sensor Value: " + sensorValue);
            Thread.sleep(1000);
        }
    }
}
