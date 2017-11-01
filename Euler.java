import java.util.*;

class Euler{
	
	private static double x;
	private static double y;
	private static double end;
	private static String expression;
	private static int divisions;
	private static double delta;
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception{
		expression = strPrompt("Please input dy/dx");
		x = dPrompt("Please input your x value");
		y = dPrompt("Please input your y value");
		end = dPrompt("Please input your ending x value");
		divisions = intPrompt("Please input your number of divisions");
		delta = (end-x)/divisions;
		System.out.println(calculate());
	}

	private static double dPrompt(String str){
		while(true){
			try{
				System.out.println(str);
				double ans = input.nextDouble();
				System.out.println();
				return ans;
			}catch(InputMismatchException e){
				System.out.println("Invalid input.\n");
				input.nextLine();
			}
		}
	}

	private static int intPrompt(String str){
		while(true){
			try{
				System.out.println(str);
				int ans = input.nextInt();
				System.out.println();
				return ans;
			}catch(InputMismatchException e){
				System.out.println("Invalid input.\n");
				input.nextLine();
			}
		}
	}

	private static String strPrompt(String str){
		while(true){
			try{
				System.out.println(str);
				String ans = input.nextLine();
				System.out.println();
				ans = ans.replaceAll("[^xy*\\-\\(\\)+/^0-9\\.sincotaar]+","");
				validate(ans);
				return ans;
			}catch(InputMismatchException e){
				System.out.println("Invalid input.\n");
			}
		}
	}

	private static void checkParen(String str)throws InputMismatchException{
		int left=0;
		int right=0;
		for(int i = 0; i<str.length();i++){
			if(str.charAt(i)=='(')left++;
			if(str.charAt(i)==')')right++;
			if(right>left)throw new InputMismatchException();
		}
		if(left==right)return;
		throw new InputMismatchException();
	}

	private static void validate(String str) throws InputMismatchException{
		checkParen(str);
		if(str.matches(".*//(//).*"))throw new InputMismatchException();
		if(str.matches(".*[(][^0-9xy(].*"))throw new InputMismatchException();
		if(str.matches(".*[^0-9xy)][)].*"))throw new InputMismatchException();
		if(str.matches(".*[)][0-9xy].*"))throw new InputMismatchException();
		if(str.matches(".*[^0-9xy()][^0-9xy\\(\\)].*"))throw new InputMismatchException();
		if(str.matches(".*[xy][0-9].*"))throw new InputMismatchException();
	}

	public static double calculate(){
		return calculate(x,y,end,expression,divisions);
	}

	public static double calculate(double x1,double y1,double end1,String expression1,int divisions1){
		expression1=expression1.replaceAll("([0-9y])x","$1*x");
		expression1=expression1.replaceAll("x([0-9y\\(])","x*$1");
		expression1=expression1.replaceAll("([0-9x])y","$1*y");
		expression1=expression1.replaceAll("y([0-9x\\(])","y*$1");
		expression1=expression1.replaceAll("\\)\\(", ")*(");
		while(divisions1-->0){
			y1+=runCalculation(expression1, x1, y1)*delta;
			x1+=delta;
		}
		return y1;
	}

	public static double runCalculation(String expression1, double x1, double y1){
		expression1=expression1.replaceAll("x", String.valueOf(x1));
		expression1=expression1.replaceAll("y", String.valueOf(y1));
		System.out.println(expression1);
		int cursor = 0;
		cursor=str.indexOf(')');
		if(cursor>0){
			int cursor1=cursor;
			while(expression1.charAt(--cursor1)!='(');
			expression1=expression1.substring(0,cursor1)+runCalculation(expression1.substring(cursor1+1,cursor),0,0)+cursor<expression1.length()-1?expression1.substring(cursor+1):"";
		}
		return 0;
	}

}