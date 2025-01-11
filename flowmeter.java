import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;

public class Caudalimetro 
{
    
    private static int pulsos = 0;
    
    public static void main(String args[]) throws InterruptedException 
    {
        System.out.println("<--Pi4J--> GPIO INTERRUPT test program");

        // create and add GPIO listener
        GpioInterrupt.addListener(new GpioInterruptListener() 
        {
            @Override
            public void pinStateChange(GpioInterruptEvent event) 
            {
                if(event.getState() == true) 
                {
                    pulsos = pulsos + 1;
                    System.out.println("Raspberry Pi PIN [" + event.getPin() +"] is in STATE [" + event.getState() + "]");
                }
            }
        });

        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) 
        {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }

        // export all the GPIO pins that we will be using
        GpioUtil.export(4, GpioUtil.DIRECTION_IN);

        // set the edge state on the pins we will be listening for
        GpioUtil.setEdgeDetection(4, GpioUtil.EDGE_BOTH);

        // configure GPIO 4 as an INPUT pin; enable it for callbacks
        Gpio.pinMode(4, Gpio.INPUT);
        GpioInterrupt.enablePinStateChangeCallback(4);

        // continuously loop to prevent program from exiting
        while (true) 
        {
            Thread.sleep(5000);
            System.out.println(pulsos);
        }
    }
}
