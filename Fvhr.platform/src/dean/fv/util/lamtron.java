package fv.util;

public class lamtron {

	public float testLamTron(float a){
		String [] h;
		String c;
		float b,d,result;
		Integer e;
			//a=54200;
			b=a/1000;
			c=""+b;
			h=c.split("\\.");
			d=Float.parseFloat(h[0]);
			e=(int)((b-d)*1000);
			if(e>=500){
				result=(d*1000)+1000;
				
			}
			/*else if(e>250){
				result=(d*1000)+500;
				
			}*/
			else if(e<500){
				result=d*1000;	
			}
			else {
				result=0;
			}
			return result;						        
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
