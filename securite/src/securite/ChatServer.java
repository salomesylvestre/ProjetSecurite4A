package securite;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatServer implements Runnable{
	PrintWriter out;
	BufferedReader in;
	Socket s;
	Scanner keyboard;
	int index;
	String input;
	boolean doRun = true;
	
	public ChatServer(Socket a, int u){
		s = a;
		keyboard = new Scanner(System.in);
		index = u;
	}
	
	public void run(){
		try{
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream());
			
			System.out.println("connexion de "+s.getInetAddress().toString()+" sur le port "+s.getPort());
			String talk = in.readLine();
			while(doRun)
			{
				while(talk == null)
				{
					talk = in.readLine();
				}
				System.out.println(talk);
				if(talk.compareToIgnoreCase("bye")==0)
				{
					System.out.println("shutting down following remote request");
					doRun = false;
				}else
				{
					System.out.print("to client#"+index+"> ");
					input = keyboard.nextLine();
					out.println(input);
					out.flush();
					if(input.compareToIgnoreCase("bye")==0)
					{
						System.out.println("server shutting down");
						doRun = false;
					}else
						talk = in.readLine();	
				}
			}
			s.close();
		}
		catch(Exception e){
			System.out.println("raaah! what did u forget this time?");
			e.printStackTrace();
		}		
	}
}
