package hw214_4;

public class Expression {
    private static enum Action { Push, Pop }
    private static Action[][] act;
    
    static {
        act = new Action[128][128]; //[stack top][input token]
        act['#']['('] = act['(']['('] = act['+']['('] = act['-']['('] = act['*']['('] = act['/']['('] = Action.Push;
                        act['('][')'] = act['+'][')'] = act['-'][')'] = act['*'][')'] = act['/'][')'] = Action.Pop;
        act['#']['+'] = act['(']['+'] =                                                                 Action.Push;
                                        act['+']['+'] = act['-']['+'] = act['*']['+'] = act['/']['+'] = Action.Pop;
        act['#']['-'] = act['(']['-'] =                                                                 Action.Push;
                                        act['+']['-'] = act['-']['-'] = act['*']['-'] = act['/']['-'] = Action.Pop;
        act['#']['*'] = act['(']['*'] = act['+']['*'] = act['-']['*'] =                                 Action.Push;
                                                                        act['*']['*'] = act['/']['*'] = Action.Pop;
        act['#']['/'] = act['(']['/'] = act['+']['/'] = act['-']['/'] =                                 Action.Push;
                                                                        act['*']['/'] = act['/']['/'] = Action.Pop;
        act['#']['$'] = act['(']['$'] = act['+']['$'] = act['-']['$'] = act['*']['$'] = act['/']['$'] = Action.Pop;
    }
    
    public static String infixToPostfix(String expr) {
        //TODO: implement this method
    	Scanner scan = new Scanner(expr);
    	Action action;
    	StackByArray<Character> operator = new StackByArray<Character>();
    	String output = "";
    	//StackByArray<Character> output = new StackByArray<Character>();
    	operator.push('#');
		for(String a : scan) {
		char c = a.charAt(0); 
			if(Scanner.isWhiteSpace(c) || Scanner.isAlpha(c)) {
				continue;
			}else if(Scanner.isDigit(c)) {
				//output.push(c);
				output += c;
			}else if((action = act[operator.top()][c]) == Action.Push) {
				operator.push(c);
			}else if (action == Action.Pop){
				char p = operator.pop();
				output += p;
				operator.push(c);
			}
		}
			
			while(operator.top() != '#') {
				if(operator.top() == '(' || operator.top() == ')') {
					operator.pop();
					continue;
				}else {
				char p = operator.pop();
				output += p;
				}
			}
			return output;
			}
			
			
			
//	    	for(int i = 0; i < copy.length(); i++) {
//    		if(Scanner.isWhiteSpace(copy.charAt(i)) || Scanner.isAlpha(copy.charAt(i))) {
//    			continue;
//    		}else if(Scanner.isDigit(copy.charAt(i))) {
//    			postfix.push(copy.charAt(i));
//    		}else if((action = act[operator.top()][copy.charAt(i)]) == Action.Push) {
//    			postfix.push(copy.charAt(i));
//    		}else {
//    			
//    		}
//    	}
			
			
        
	
    			
//    		}else {
//    			while((action = act[operator.top()][c]) == Action.Pop) {
//    				char p = operator.pop();
//    				output += p;
//        			//output.push(p);
//        			operator.push(c);
//    			}
//    			if(action == Action.Push) {
//    				operator.push(c);
//    			}
//    		}
//    	}
    			
    	
//    	String copy = "";
//    	for(int i = 0; i < output.size(); i++) {
//    		copy += output.pop().toString();
//    	}
	    
    	
    		
    	
//    	for(int i = 0; i < copy.length(); i++) {
//    		if(Scanner.isWhiteSpace(copy.charAt(i)) || Scanner.isAlpha(copy.charAt(i))) {
//    			continue;
//    		}else if(Scanner.isDigit(copy.charAt(i))) {
//    			postfix.push(copy.charAt(i));
//    		}else if((action = act[operator.top()][copy.charAt(i)]) == Action.Push) {
//    			postfix.push(copy.charAt(i));
//    		}else {
//    			
//    		}
//    	}
    		
    		
    		

    		
    
    public static double evalPostfixExpr(String expr) {
        //TODO: implement this method
    	StackByArray<Double> num = new StackByArray<Double>();
    	for(int i = 0; i < expr.length(); i++) {
    		char c = expr.charAt(i);
    		if(c == '$') {
    			break;
    		}
    		if(Scanner.isWhiteSpace(c) || Scanner.isAlpha(c)) {
    			continue;
    		}else if(Scanner.isDigit(c)){
    			num.push((Double.parseDouble(String.valueOf(c))));
    		}else {
    			double num1 = num.pop();
    			double num2 = num.pop();
    			
    			if(c == '+') {
    				num.push(num1 + num2);
    			}else if(c == '-') {
    				num.push(num2 - num1);
    			}else if(c == '*') {
    				num.push(num1 * num2);
    			}else if(c == '/') {
    				num.push(num2 / num1);
    			}
    		}
    	}
        return num.pop();
    }    
}



















