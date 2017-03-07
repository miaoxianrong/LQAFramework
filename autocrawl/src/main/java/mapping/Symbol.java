package mapping;

public class Symbol {
	final static String[] symbol = {"[[...]]","{{...}}","«...»"};
	public Symbol(){
		
	}
	public static String getLeftByIndex(int index){
		String symbol_left = symbol[index];
		symbol_left = symbol_left.substring(0, symbol_left.indexOf("."));
		return symbol_left;
	}
	public static String getRightByIndex(int index){
		String symbol_r = symbol[index];
		symbol_r = symbol_r.substring((symbol_r.lastIndexOf(".")+1),symbol_r.length());
		return symbol_r;
	}
}
