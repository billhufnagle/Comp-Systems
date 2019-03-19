// To run these two programs, open two separate terminal windows
//then, use cd to change to the directory of these two files, most likely
//somewhere in your downloads folder.
//then, compile the two files, assuming you have the correct classpaths for java
//this will simply be javac serverbitcoin.java, then press enter
//and javac clientbitcoin.java, warnings may arrise but as long as it doesn't
// say error you are all set. Then in one of the terminal windows
//** type java serverbitcoin then press enter and in the other
// type java clientbitcoin then press enter
//the clientbitcoin should start counting by 500,000s, this means its working
//it will end and print out 2 large hexadecimal numbers, the top being the hash\
// of the header, the bottom being the target
//at which point the serverbitcoin terminal will print out yes if it was accepted
//if that is printed then the puzzle was correctly solved and verified
//that is the end of the program, it can be rerun by following the steps starting
//at the double asterisk(**)
//much of this code came from the geeks for geeks website provided in the
//assignment, modified to fit this purpose
import java.net.*;
import java.io.*;

public class clientbitcoin
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private ObjectOutputStream out     = null;

    // constructor to put ip address and port
    public clientbitcoin(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
            // I made my block header serializable because the files
            //blockencodings.h and blockencodings.cpp pointed to that
            //being the way that the header is sent as a message.
            //this allowed for the whole object to be given as an input
            out    = new ObjectOutputStream(socket.getOutputStream());

            // sends output to the socket

        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        Bitcoinheader miner = new Bitcoinheader("352FFFFFFF");
        // string to read message from input
        miner.puzzle();

        try
          {
            out.writeObject(miner);
          }
          catch(IOException i)
          {
            System.out.println(i);
          }


        // close the connection
        try
        {
          out.close();
          socket.close();
        }
        catch(IOException i)
        {
          System.out.println(i);
        }
        return;
    }

    public static void main(String args[])
    {
        clientbitcoin client = new clientbitcoin("127.0.0.1", 3000);
    }
}
