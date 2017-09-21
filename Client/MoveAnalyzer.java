package Client;

public class MoveAnalyzer {
	private String from1 = "";
	private int from = 0;
	private String to1 = "";
	private int to = 0;
	
	public void setFrom1(String from1){this.from1 = from1;}
	public void setFrom(int from){this.from = from;}
	public void setTo1(String to1){this.to1 = to1;}
	public void setTo(int to){this.to = to;}
	
	public String getFrom1(){return from1;}
	public int getFrom(){return from;}
	public String getTo1(){return to1;}
	public int getTo(){return to;}
	
	public String moveEncoder(char a1, int a, char b1, int b){/*
		String result = "";
		switch(a1){
			case 'A':result = result + "A";
				break;
			case 'B':result = result + "B";
				break;
			case 'C':result = result + "C";
				break;
			case 'D':result = result + "D";
				break;
			case 'E':result = result + "E";
				break;
			case 'F':result = result + "F";
				break;
			case 'G':result = result + "G";
				break;
			case 'H':result = result + "H";
				break;
			default:
				break;
		}
		switch(a){
			case 1:result = result + "1";
				break;
			case 2:result = result + "2";
				break;
			case 3:result = result + "3";
				break;
			case 4:result = result + "4";
				break;
			case 5:result = result + "5";
				break;
			case 6:result = result + "6";
				break;
			case 7:result = result + "7";
				break;
			case 8:result = result + "8";
				break;
			default:
				break;
		}
		switch(b1){
			case 'A':result = result + "A";
				break;
			case 'B':result = result + "B";
				break;
			case 'C':result = result + "C";
				break;
			case 'D':result = result + "D";
				break;
			case 'E':result = result + "E";
				break;
			case 'F':result = result + "F";
				break;
			case 'G':result = result + "G";
				break;
			case 'H':result = result + "H";
				break;
			default:
				break;
		}
		switch(b){
			case 1:result = result + "1";
				break;
			case 2:result = result + "2";
				break;
			case 3:result = result + "3";
				break;
			case 4:result = result + "4";
				break;
			case 5:result = result + "5";
				break;
			case 6:result = result + "6";
				break;
			case 7:result = result + "7";
				break;
			case 8:result = result + "8";
				break;
			default:
				break;
		}
		return result;*/
		return "" + a1 + a + b1 + b;
	}
	
	public void moveDecoder(String fromMove){
		char from2 = 'z';
		char to2 = 'z';
		boolean isFirstNumber = true;
		boolean isFirstLetter = true;
		for(int x = 0; x< fromMove.length();x++){
			if(fromMove.charAt(x) == '1'){
				if(isFirstNumber){
					from = 1;
					isFirstNumber = false;
				}
				else
					to = 1;
			}
			else if(fromMove.charAt(x) == '2'){
				if(isFirstNumber){
					from = 2;
					isFirstNumber = false;
				}
				else
					to = 2;
			}
			else if(fromMove.charAt(x) == '3'){
				if(isFirstNumber){
					from = 3;
					isFirstNumber = false;
				}
				else
					to = 3;
			}
			else if(fromMove.charAt(x) == '4'){
				if(isFirstNumber){
					from = 4;
					isFirstNumber = false;
				}
				else
					to = 4;
			}
			else if(fromMove.charAt(x) == '5'){
				if(isFirstNumber){
					from = 5;
					isFirstNumber = false;
				}
				else
					to = 5;
			}
			else if(fromMove.charAt(x) == '6'){
				if(isFirstNumber){
					from = 6;
					isFirstNumber = false;
				}
				else
					to = 6;
			}
			else if(fromMove.charAt(x) == '7'){
				if(isFirstNumber){
					from = 7;
					isFirstNumber = false;
				}
				else
					to = 7;
			}
			else if(fromMove.charAt(x) == '8'){
				if(isFirstNumber){
					from = 8;
					isFirstNumber = false;
				}
				else
					to = 8;
			}
			if(fromMove.charAt(x) == 'A' || fromMove.charAt(x) == 'B' || fromMove.charAt(x) == 'C' || 
				fromMove.charAt(x) == 'D' || fromMove.charAt(x) == 'E' || fromMove.charAt(x) == 'F' || 
				fromMove.charAt(x) == 'G' || fromMove.charAt(x) == 'H'){
				if(isFirstLetter){
					from2 = fromMove.charAt(x);
					isFirstLetter = false;
				}
				else{
					to2 = fromMove.charAt(x);
				}
			}
		}
		from1 = "" + from2;
		to1 = "" + to2;
	}
}
