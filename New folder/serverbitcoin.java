
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
import java.net.*;
import java.io.*;
import java.util.Date;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;

public class serverbitcoin
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private ObjectInputStream in       =  null;

    // constructor with port
    public serverbitcoin(int port) throws ClassNotFoundException
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new ObjectInputStream(socket.getInputStream());

            // reads message from client until "Over" is sent
            try
              {
              Bitcoinheader header = (Bitcoinheader) in.readObject();
              String targ = header.Target.substring(2,8);
              //code for checking this new hash for being under the target
              String blockheader = header.prevheader+header.merkleroot+Long.toString(header.Time)+header.Target;
              byte[] headerdigest;
              String headercheck;
              String headerhash;
              MessageDigest md = MessageDigest.getInstance("SHA-256");
              for(int i = 0; i< Integer.parseInt(header.Target.substring(0,2), 16); i++){
                targ = targ +"0";//this is the method for taking the target in shortened
              }// bit form and turning into the correct number in hex, based on the exponent
              while (targ.length()<64){
                targ = "0"+targ;
              }

              //System.out.println(this.Nonce);
              headercheck = blockheader + header.Nonce;
              headerdigest = md.digest(headercheck.getBytes());
              headerhash = DatatypeConverter.printHexBinary(headerdigest);
              System.out.println(headerhash);
              System.out.println(targ);
              if (headerhash.compareTo(targ)<0){
                System.out.println("Yes, accepted");
              }else{
                System.out.println("not accepted");
              }
              }
              catch(IOException i)
              {
                System.out.println(i);
              }

            // close connection
            socket.close();
            in.close();
        }
        catch(IOException | NoSuchAlgorithmException e)
        {
            System.out.println(e);
        }
    }

    public static void main(String args[]) throws ClassNotFoundException
    {
        serverbitcoin server = new serverbitcoin(3000);
    }
}
