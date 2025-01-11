import com.pi4j.io.serial.*;
import com.pi4j.util.Console;
import java.util.Arrays;

import java.io.IOException;

public class SerialTXRX
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		final Console console = new Console();
		console.title("Send and Recieve data from serial port");
		console.promptForExit();
		
		final Serial serial = SerialFactory.createInstance();
		
		//serial.setBufferingDataReceived(false);
		
		try {
		    SerialConfig config = new SerialConfig();
		    
		    config.device(RaspberryPiSerial.AMA0_COM_PORT)
			  .baud(Baud._9600)
			  .dataBits(DataBits._8)
			  .parity(Parity.NONE)
			  .stopBits(StopBits._1)
			  .flowControl(FlowControl.NONE);

		    console.box(" Connecting to: " + config.toString());

		    serial.open(config);

		    while(console.isRunning()) {
			try 
			{
			    serial.write("AT\r\n");
			    
			    int available = serial.available();
			    
			    if(available > 0) 
			    {
				byte[] data = serial.read();
				System.out.print("[" + available + " BYTES AVAILABLE] : ");
				System.out.print(Arrays.toString(data));
				String st = new String(data);
				System.out.println(" " + st);
				//break;
			    } 
			    else 
			    {
				System.out.println("[NO DATA AVAILABLE]");
			    }
			}
			catch(IllegalStateException ex)
			{
			    ex.printStackTrace();
			}
			
			Thread.sleep(1000);
		    }

		    serial.close();
		}
		catch(IOException ex) 
		{
		    console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
		    return;
		}
		
	}
}
