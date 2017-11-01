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
				ans = ans.replaceAll("[^xy*\\-\\(\\)+/^0-9\\.]+","");
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
		if(str.isEmpty())throw new InputMismatchException();
		if(str.matches(".*[\\.][0-9]*[\\.].*"))throw new InputMismatchException();
		if(str.matches(".*[(][^0-9xy(-].*"))throw new InputMismatchException();
		if(str.matches(".*[^0-9xy)][)].*"))throw new InputMismatchException();
		if(str.matches(".*[)][0-9xy].*"))throw new InputMismatchException();
		if(str.matches(".*[^0-9xy()][^0-9xy()-].*"))throw new InputMismatchException();
		if(str.matches(".*[xy][0-9].*"))throw new InputMismatchException();
	}

	public static double calculate(){
		return calculate(x,y,end,expression,divisions);
	}

	private static double calculate(double x1,double y1,double end1,String expression1,int divisions1){
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

	private static double runCalculation(String expression1, double x1, double y1){
		expression1=expression1.replaceAll("x", String.valueOf(x1));
		expression1=expression1.replaceAll("y", String.valueOf(y1));
		int cursor=expression1.indexOf(')');
		while(cursor>0){
			int cursor1=cursor;
			while(expression1.charAt(--cursor1)!='(');
			expression1=expression1.substring(0,cursor1)+runCalculation(expression1.substring(cursor1+1,cursor),0,0)+(cursor<(expression1.length()-1)?expression1.substring(cursor+1):"");
			cursor=expression1.indexOf(')');
		}
		while(!expression1.matches("[-]?[0-9]+[\\.]?[0-9]*")){
			while(expression1.indexOf('^')>=0) {
				cursor=expression1.length()-1;
				while(expression1.charAt(--cursor)!='^');
				double base;
				double exponent;
				int cursor1=cursor;
				while(--cursor1>=0&&"0123456789.".indexOf(expression1.charAt(cursor1))>=0);
				cursor1++;
				if((cursor1>1&&expression1.charAt(cursor1-1)=='-'&&"+*/^(-".indexOf(expression1.charAt(cursor1-2))>=0)||(cursor1==1&&expression1.charAt(0)=='-'))cursor1--;
				base=Double.parseDouble(expression1.substring(cursor1,cursor));
				int cursor2= cursor+1;
				while(++cursor2<expression1.length()&&"0123456789.".indexOf(expression1.charAt(cursor2))>=0);
				exponent = Double.parseDouble(expression1.substring(cursor+1,cursor2));
				double answer = Math.pow(base, exponent);
				expression1=expression1.substring(0,cursor1)+answer+expression1.substring(cursor2);
			}
			while(expression1.indexOf('/')>=0||expression1.indexOf('*')>=0){
				if(expression1.indexOf('/')<0){
					cursor=expression1.indexOf('*');
				}else if(expression1.indexOf('*')<0){
					cursor=expression1.indexOf('/');
				}else{
					cursor=Math.min(expression1.indexOf('/'),expression1.indexOf('*'));
				}
				double first;
				double second;
				int cursor1=cursor;
				while(--cursor1>=0&&"0123456789.".indexOf(expression1.charAt(cursor1))>=0);
				cursor1++;
				if((cursor1>1&&expression1.charAt(cursor1-1)=='-'&&"+*/^(-".indexOf(expression1.charAt(cursor1-2))>=0)||(cursor1==1&&expression1.charAt(0)=='-'))cursor1--;
				first=Double.parseDouble(expression1.substring(cursor1,cursor));
				int cursor2= cursor+1;
				while(++cursor2<expression1.length()&&"0123456789.".indexOf(expression1.charAt(cursor2))>=0);
				second = Double.parseDouble(expression1.substring(cursor+1,cursor2));
				double answer;
				if(expression1.charAt(cursor)=='*'){
					answer=first*second;
				}
				else{
					answer=first/second;
				}
				expression1=expression1.substring(0,cursor1)+answer+expression1.substring(cursor2);
			}
			while(expression1.indexOf('-')>=0||expression1.indexOf('+')>=0){
				if(expression1.indexOf('-')<0){
					cursor=expression1.indexOf('+');
				}else if(expression1.indexOf('+')<0){
					cursor=expression1.indexOf('-');
				}else{
					cursor=Math.min(expression1.indexOf('-'),expression1.indexOf('+'));
				}
				double first;
				double second;
				int cursor1=cursor;
				while(--cursor1>=0&&"0123456789.".indexOf(expression1.charAt(cursor1))>=0);
				cursor1++;
				if((cursor1>1&&expression1.charAt(cursor1-1)=='-'&&"+*/^(-".indexOf(expression1.charAt(cursor1-2))>=0)||(cursor1==1&&expression1.charAt(0)=='-'))cursor1--;
				first=Double.parseDouble(expression1.substring(cursor1,cursor));
				int cursor2= cursor+1;
				while(++cursor2<expression1.length()&&"0123456789.".indexOf(expression1.charAt(cursor2))>=0);
				second = Double.parseDouble(expression1.substring(cursor+1,cursor2));
				double answer;
				if(expression1.charAt(cursor)=='+'){
					answer=first+second;
				}
				else{
					answer=first-second;
				}
				expression1=expression1.substring(0,cursor1)+answer+expression1.substring(cursor2);
			}
			
		}
		return Double.parseDouble(expression1);
	}

}