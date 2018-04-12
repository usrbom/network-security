import java.io.*;
import java.util.*;

public class ObliviousTransfer{

	public long power(long x, long y, long p)
    {
        long res = 1;     
        x = x % p; 
     
        while (y > 0)
        {
            if((y & 1)==1)
                res = (res * x) % p;

            y = y >> 1; 
            x = (x * x) % p; 
        }
        return res;
    }

	public static void main(String[] args) {

	Alice alice = new Alice();
	Bob bob = new Bob();

	//choose message
	alice.chooseMessage();

	//calculate X:
	alice.calculateX();
	bob.e = alice.e;
	bob.N = alice.N;
	System.out.println("Alice passing random value x0 & x1 along with modulus N = "+bob.N+" & public exponent e = "+bob.e);

	// Bit generator & message selection by Bob:
	bob.selectBit();
	bob.selectMessage(alice.x0, alice.x1);

	//generate k 
	bob.generateK();
	long v =bob.getV();

	//At Alice's end
	alice.calculateK(v);
	long nm1 = alice.getMessage1();
	long nm2 = alice.getMessage2();

	//at Bob's end
	bob.printMessage(nm1, nm2);
	
	}
}
class Alice{
	//choosing a RSA pair:
	public long N = 4699 , e = 13 ;
	private long d = 349;

	public int x0, x1;
	public long v , k0 ,k1;

	long m0 , m1 ;

	public void chooseMessage(){
		Scanner sc =new Scanner(System.in);
		System.out.println("Choose message m0 = ");
		m0 = sc.nextLong();
		System.out.println("Choose message m1 = ");
		m1 = sc.nextLong();
		System.out.println();
	}

	public void calculateX(){
	//random values x0,x1 chosen.
	 x0 = (int)(Math.random() * 100);
	 x1 = (int)(Math.random() * 100);
	 System.out.println("Alice generating x0 = "+x0);
	 System.out.println("Alice generating x1 = "+x1);
	}	

	public void calculateK( long v){
		this.v = v;
		ObliviousTransfer obv = new ObliviousTransfer();
		 k0 = (obv.power(v-x0, d, N))  ;
		 k1 = (obv.power(v-x1, d, N))  ;
		System.out.println("Alice calculating k0 = "+k0);
		System.out.println("Alice calculating k1 = "+k1);
	}
	public long getMessage1(){
		System.out.println("Message 1 sent to Bob = (k0 + m0) = "+(m0 + k0));
		return m0 + k0;
	}
	public long getMessage2(){
		System.out.println("Message 2 sent to Bob = (k1 + m1) = "+(m1 + k1));
		return m1 + k1;
	}

}
class Bob{

	private int bit , xb , k ;

	public long v, e ,N ;

	public void selectBit(){
		//Bob choosing bit 0 or 1 randomly
		bit = (int)Math.round(Math.random());
	}

	public void selectMessage(int x0, int x1){
		if(bit==0)
			xb = x0;
		else
			xb = x1;
		System.out.println("Bob selcting message "+bit+" , hence xb = "+xb);
	}

	public void generateK(){
		k = (int)(Math.random()*100);
		System.out.println("Bob chooses k = "+k);
	}

	public long getV(){
		ObliviousTransfer obv = new ObliviousTransfer();
		 v = (xb + (obv.power(k, e, N))) % N ;
		 System.out.println("Bob calculates v = "+v);
		 while(!(v - xb > 0 )){
		 	Bob b = new Bob();
		 	b.generateK();
		 	v = (xb + (obv.power(k, e, N))) % N ;
		 }
		 return v;
	}

	public void printMessage(long nm0, long nm1){
		if(bit==0)
			System.out.println("Deciphered message = "+(nm0-k));
		else
			System.out.println("Deciphered message = "+(nm1-k));
		System.out.println("********************");
		}
}
