import java.util.Date;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;


public class Bitcoinheader{
  public String hash;
  public String Nonce = "00000000";
  public String prevheader;
  public String merkleroot;
  public Long Time;
  public String Target;//in hex
  public String stringforprev;
  public String stringformerkle;

  public Bitcoinheader(String target){
    stringforprev = new String("thisistherandomstringbeingusedfortheprevheaderhashaskjiowqurncmx");
    stringformerkle = new String("notmuchherejustthestringforthemerklerootwhichshouldberandomenoughas;dlfjasdf");
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      byte[] messageDigest = md.digest(stringforprev.getBytes());
      prevheader = DatatypeConverter.printHexBinary(messageDigest);
      byte[] messageDigestroot = md.digest(stringformerkle.getBytes());
      merkleroot = DatatypeConverter.printHexBinary(messageDigestroot);
    }
    catch(NoSuchAlgorithmException e){
      System.out.println("no such algorithm");
    }

    //System.out.println(prevheader);
    //System.out.println(merkleroot);
    Time = new Date().getTime();
    Target = target;//in hex
    //System.out.println(Time);

  }

  public void puzzle() {
    try{
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    Long starttime = new Date().getTime();
    String targ = this.Target.substring(2,8);
    for(int i = 0; i< Integer.parseInt(this.Target.substring(0,2), 16); i++){
      targ = targ +"0";//this is the method for taking the target in shortened
    }// bit form and turning into the correct number in hex, based on the exponent
    while (targ.length()<64){
      targ = "0"+targ;
    }
    String header = this.prevheader+this.merkleroot+Long.toString(this.Time)+this.Target;
    byte[] headerdigest;
    String headercheck;
    String headerhash;
    int count = 0;
    do {
      //System.out.println(this.Nonce);
      this.Nonce = Long.toHexString(Long.parseLong(this.Nonce, 16)+1);
      //System.out.println(this.Nonce);
      headercheck = header + this.Nonce;
      headerdigest = md.digest(headercheck.getBytes());
      headerhash = DatatypeConverter.printHexBinary(headerdigest);
      count ++;



    }while (headerhash.compareTo(targ)>=0);
    this.hash=headerhash;
    System.out.println(count);
    System.out.println(headerhash);
    System.out.println(targ);
    }
    catch(NoSuchAlgorithmException e){
      System.out.println("no such algorithm");
    }
  }

  public static void main(String[] args){
    Bitcoinheader newheader = new Bitcoinheader("35200000");
    System.out.println("startingpuzzle");
    newheader.puzzle();
    System.out.println("puzzle done");

  }
}
