import java.util.Date;
import java.util.Formatter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;


public class Bitcoinheader implements Serializable{
  public String hash;
  public String Nonce = "00000000";
  public String prevheader;
  public String merkleroot;
  public Long Time;
  public String Target;//in hex
  public String stringforprev;
  public String stringformerkle;
  public String headerhash;


  public Bitcoinheader(String target){
    stringforprev = new String("thisistherandomstringbeingusedfortheprevheaderhashaskjiowqurncmx");
    stringformerkle = new String("notmuchherejustthestringforthemerklerootwhichshouldberandomenoughas;dlfjasdf");
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      byte[] messageDigest = md.digest(stringforprev.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : messageDigest) {
        sb.append(String.format("%02X", b));
      }
      System.out.println(sb.toString());
      prevheader = sb.toString();
      System.out.println(prevheader);
      byte[] messageDigestroot = md.digest(stringformerkle.getBytes());
      StringBuilder nb = new StringBuilder();
      for (byte b : messageDigestroot) {
        nb.append(String.format("%02X", b));
      }
      merkleroot = nb.toString();
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
      StringBuilder hh = new StringBuilder();
      for (byte b : headerdigest) {
        hh.append(String.format("%02X", b));
      }
      headerhash = hh.toString();
      count ++;
      if (count%100000 == 0){
        System.out.println(count);
      }



    }while (headerhash.compareTo(targ)>=0);
    this.hash=headerhash;
    System.out.println(count);
    System.out.println(headerhash);
    System.out.println(targ);
    this.headerhash = headerhash;
    }
    catch(NoSuchAlgorithmException e){
      System.out.println("no such algorithm");
    }
  }

  public static void main(String[] args){
    Bitcoinheader newheader = new Bitcoinheader("35B00000");
    System.out.println("1".compareTo("a"));
    System.out.println("startingpuzzle");
    newheader.puzzle();
    System.out.println("puzzle done");

  }
}
