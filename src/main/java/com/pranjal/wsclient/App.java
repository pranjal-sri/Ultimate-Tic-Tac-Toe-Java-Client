package com.pranjal.wsclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;
import javax.websocket.DeploymentException;

import org.glassfish.tyrus.server.Server;

import com.pranjal.wsclient.grid.MainContainer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println("inside "+ App.class.getName());
        SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainContainer();				
			}
		});
    }

	private static void runServer() {
		Server server = new Server("localhost", 8025, "/websockets", TestServerEndPoint.class);
		
		try {
			server.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Press any key to stop the server.");
			
			reader.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			server.stop();
		}
		
	}
}
