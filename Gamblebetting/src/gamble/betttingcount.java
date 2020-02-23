package gamble;

public class betttingcount {

	public static void main(String[] args) {
		
	//int total = solve(n,k);
	//System.out.println("value"+ total);
		
		
		// TODO Auto-generated method stub
	int n =20;//18;//8; //100;
	int k =2;//2;//0; //5;
	int bettingcount = 0; //0
	if(k==0) {
		System.out.println("The min number of times John has to bet is "+(n-1));
	}else {
	    for(int i= 0; i<=k;i++) {
	    	if(n%2 == 0) {
	    		bettingcount++;
	    		n=n/2;
	    	}
	    	 else {
	    		 n=n-1;
	    		 bettingcount++;
	    		}
	  }
	    int totalcount = (n-1)+bettingcount;
	    System.out.println("The min number of times John has to bet is "+ totalcount);
	}
	}
	}
	/*
	static int solve(double d, int k) {
		if(d<=0) {
			return 0;
		}
		if(k==0) {
			return (int) (d-1);
		}
		
			return 1+solve(Math.ceil(d/2),k-1);
		
	}*/

