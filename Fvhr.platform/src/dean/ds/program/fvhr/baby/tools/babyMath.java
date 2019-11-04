package ds.program.fvhr.baby.tools;

/**
 * 
 * @author baby
 *
 */
public class babyMath{

	/**trả về số được làm tròn
	 *@param number : số cần được làm tròn
	 *@param roundNumber : làm tròn bao nhiêu số sau dấu "," 
	 *@see  Round(0.125,2) = 0.13
	 */
	public static double Round(double number, int roundNumber)
	{
		double temp = 10;
		number = 0.125;
		roundNumber = 1;
		temp = Math.pow(temp, roundNumber);
		number = Math.round(number * temp)/temp;
		return number;
	}
	public static double Round(float number, int roundNumber)
	{
		double temp = 10;
		temp = Math.pow(number, roundNumber);
		number = Math.round(number * temp)/100f;
		return number;
	}
}