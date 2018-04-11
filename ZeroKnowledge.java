import java.io.*;
import java.util.*;

 class ZeroKnowldege{

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

    public static int g  , p ;
    // 23, 1237
	 public static void main(String[] args) {
		Peggy peggy = new Peggy();
		Victor victor =new Victor();
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose generator g: ");
		g = sc.nextInt();
		System.out.println("Choose prime p: ");
		p = sc.nextInt();
		//calculate y:
		long y = peggy.calculateY(g,p);
		victor.setY(y);

		//Iterations:
		System.out.println("No. of rounds of checking:");
		int iterations =sc.nextInt();
		peggy.peggyKnows(g,p,iterations,victor);

	}
}
class Peggy{

	//predecided x which is the hidden information.
	int x = 74;
	long y;

	public long calculateY(int g ,int p){
		ZeroKnowldege zk = new ZeroKnowldege();
		y =	zk.power(g,x,p);
		return y;
	}
	public void peggyKnows(int g, int p, int i, Victor victor){
		ZeroKnowldege zk =new ZeroKnowldege();
		System.out.println();
		for(int j=1;j<=i;j++){
			System.out.println("Round: "+j);

			//choose random r
			int r = (int)(Math.random()*1000);
			System.out.println("Peggy chooses random r :"+r);
			long C = zk.power(g,r,p);
			System.out.println("Peggy sends Victor computed C ="+g+"^"+r+" mod "+p+"  = "+C);
			victor.sendC(C);

			//Victor chooses request
			int request = victor.request(); 
			System.out.println("Victor Requesting for :"+request);

			if(request==0){
				if((victor.checkresponse(g,r,p,r,request)==false))
					break;
			}
			else{
				long val = zk.power(x+r,1,p-1);
				if(victor.checkresponse(g,r,p,val,request)==false)
					break;
			}
		System.out.println("*********");
		}
	}

}
class Victor{
	
	long y , C ;

	public void setY(long y){
		System.out.println("Victor recieves y = "+y);
		this.y = y;
	}

	public void sendC(long C){
		System.out.println("Victor recieves C ="+C);
		this.C = C ;
	}
	public int request(){
		//request = 0 implies Victor is asking for r 
		//request = 1 implies Victor is asking for (x+r)mod(p-1)
		return ((int)Math.round(Math.random()));
	}
	public boolean checkresponse(int g ,int r,int p, long val ,int request){
		ZeroKnowldege zk =new ZeroKnowldege();
		if(request==0){
			long newC = zk.power(g,val,p);
			System.out.println("Victor's New C = "+g+"^"+val+" mod "+p+"  = "+newC);
			if(newC == C)
				System.out.println("Alright");      //Peggy is not bluffing.
			else{
				System.out.println("Peggy lies");
				return false;
			}
			return true;
		}
		else{
			long newC = zk.power(g,val,p);
			long matchwith = zk.power(C*y,1,p);
			System.out.println("Victor's New C:"+newC+" which should be equal to ("+C+"x"+y+")mod "+p+"="+(matchwith));
			if(newC == matchwith){
				System.out.println("Alright");    //Peggy is not bluffing.
				return true;
			}
			else
				System.out.println("Peggy lies");
			return false;
		}
	}

}