import java.util.Calendar;


public class C {
public static void main(String a[])

{
	Calendar c =Calendar.getInstance();
	c.set(Calendar.YEAR, 2016);
	c.set(Calendar.MONTH, 1);
	c.set(Calendar.DATE, 1);		
			
	System.out.println(c.getTimeInMillis());
}
}
