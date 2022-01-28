package hw214_7;

public class Parser {
	private static class Node {
		public Integer num;
		public Character opr;

		public Node(Integer num) {
			this.num = num;
		}

		public Node(Character opr) {
			this.opr = opr;
		}

		public String toString() {
			return num != null ? num.toString() : opr != null ? opr.toString() : "";
		}
	}

	private static enum Action {
		Push, Pop
	}

	private static Action[][] act;

	static {
		act = new Action[128][128]; // [stack top][input token]
		act['#']['('] = act['(']['('] = act['+']['('] = act['-']['('] = act['*']['('] = act['/']['('] = Action.Push;
		act['('][')'] = act['+'][')'] = act['-'][')'] = act['*'][')'] = act['/'][')'] = Action.Pop;
		act['#']['+'] = act['(']['+'] = Action.Push;
		act['+']['+'] = act['-']['+'] = act['*']['+'] = act['/']['+'] = Action.Pop;
		act['#']['-'] = act['(']['-'] = Action.Push;
		act['+']['-'] = act['-']['-'] = act['*']['-'] = act['/']['-'] = Action.Pop;
		act['#']['*'] = act['(']['*'] = act['+']['*'] = act['-']['*'] = Action.Push;
		act['*']['*'] = act['/']['*'] = Action.Pop;
		act['#']['/'] = act['(']['/'] = act['+']['/'] = act['-']['/'] = Action.Push;
		act['*']['/'] = act['/']['/'] = Action.Pop;
		act['#']['$'] = act['(']['$'] = act['+']['$'] = act['-']['$'] = act['*']['$'] = act['/']['$'] = Action.Pop;
	}

	public static BinaryTree<Node> parseExpr(String expr) {
		Scanner scan = new Scanner(expr);
		Stack<LinkedBinaryTree<Node>> tree = new StackByArray<LinkedBinaryTree<Node>>(); // 전체 tree
		Stack<Character> opr = new StackByArray<Character>();

		// TODO: - parseExpr will be similar to evalExpr function that evaluates
		// infix expressions.
		// - Here, instead of using the operand stack, we push/pop subtrees of
		// the parse tree to/from the tree stack.
		// - When popping an operator, pop two parse-trees from the tree stack;
		// build a parse-tree rooted at the operator; and push the resulting tree
		// onto the tree stack
		Action action;
		opr.push('#');

		for (String a : scan) {
			char c = a.charAt(0);
			if (Scanner.isWhiteSpace(c) || Scanner.isAlpha(c)) {
				continue;
			} else if (Scanner.isDigit(c)) {
				LinkedBinaryTree<Node> subtree = new LinkedBinaryTree<Node>();
				Integer i = new Integer(a);
				Node n = new Node(i);
				subtree.addRoot(n);
				tree.push(subtree);
			} else {
				if (c == '$') {
					while (opr.top() != '#') {
						LinkedBinaryTree<Node> subtree = new LinkedBinaryTree<Node>();
						while (opr.top().equals('(') || opr.top().equals(')')) {
							opr.pop();
						}
						Node x = new Node(opr.pop());
						LinkedBinaryTree<Node> t1 = tree.pop();
						LinkedBinaryTree<Node> t2 = tree.pop();
						subtree.attach(subtree.addRoot(x), t2, t1);
						tree.push(subtree);
					}
				
				} else if ((action = act[opr.top()][c]) == Action.Push) {
					// 연산자
					Character cc = new Character(c);
					opr.push(cc);
				} else if (action == Action.Pop) {
					LinkedBinaryTree<Node> subtree = new LinkedBinaryTree<Node>();

					LinkedBinaryTree<Node> t2 = tree.pop();
					LinkedBinaryTree<Node> t1 = tree.pop();

					Node n = new Node(opr.pop());
					subtree.attach(subtree.addRoot(n), t1, t2);

					Character cc = new Character(c);
					opr.push(cc);
					tree.push(subtree);
				}
			}
		}
		return tree.pop();
	}
	
	
	
	
	
	
	
	
	//1 + 2 * 3 / (4 ‐ 5)
	//
	//                /
	//         *          -
	//     +    3     4   5
	//  1    2
	

	public static double evalExpr(BinaryTree<Node> tree) {
        LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) tree;
        Stack<Double> num = new StackByArray<Double>();
        
        //TODO: - evalExpr will be similar to evalPostfixExpr function that evaluates
        //        postfix expressions.
        //      - While traversing the nodes of the parseTree in the post-order,
        //        evaluate the expression by pushing/popping operands to/from the stack num 
        for (Position<Node> p : parseTree.postorder()) {
        	
        	if(p.getElement().opr == null) {
        		//num일 때 
        		num.push(((double)p.getElement().num));
        	}else {
        		//opr
        		char c = p.getElement().opr;
        		if(p.getElement().opr.equals('#')) {
            		continue;
            	}
        		if(c == '+') {
        			double d1 = num.pop();
        			double d2 = num.pop();
        			num.push(d1 + d2);
        		}else if(c == '-') {
        			double d1 = num.pop();
        			double d2 = num.pop();
        			num.push(d2 - d1);
        		}else if(c == '*') {
        			double d1 = num.pop();
        			double d2 = num.pop();
        			num.push(d1 * d2);
        		}else if(c == '/') {
        			double d1 = num.pop();
        			double d2 = num.pop();
        			num.push(d2 / d1);
        		}else if(c == '$') {
        			break;
        		}
        	}
        }
        return num.top();
	}

	public static String infixToPrefix(String expr) {
		String strExp = "";
		LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) parseExpr(expr);
		for (Position<Node> p : parseTree.preorder())
			strExp += p.getElement() + " ";
		return strExp;
	}

	public static String infixToPostfix(String expr) {
		String strExp = "";
		LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) parseExpr(expr);
		for (Position<Node> p : parseTree.postorder())
			strExp += p.getElement() + " ";
		return strExp;
	}

	public static String infixToInfix(String expr) {
		String strExp = "";
		LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) parseExpr(expr);
		for (Position<Node> p : parseTree.inorder())
			strExp += p.getElement() + " ";
		return strExp;
	}
}
