/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.SSLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SSTokenType;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.tecaj.hw3.prob1.LexerException;
import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * SmartScriptParser has a single constructor which accepts a string that contains 
 * document body. In this constructor, parser creates an instance of lexer and initialize
 * it with obtained text. The parser use lexer for production of tokens.
 * 
 * @author Stjepan
 *
 */
public class SmartScriptParser {	
	/**
	 * lexer used for production of tokens
	 */
	private final SmartScriptLexer lexer;
	
	/**
	 * stack to make tree construction
	 */
	private ObjectStack stack;
	
	
	/**
	 * In this constructor, parser creates an instance of lexer and initialize
	 * it with obtained text.
	 * 
	 * @param documentBody text to initialize lexer
	 * @throws SmartScriptParserException if argument is null
	 */
	public SmartScriptParser(String documentBody) {
		if (documentBody == null) {
			throw new SmartScriptParserException("Document body must not be null.");
		}
		
		stack = new ObjectStack();
		stack.push(new DocumentNode());
		lexer = new SmartScriptLexer(documentBody);// will never throw
		parseDocument();
	}
	
	/**
	 * Method to parse tokens from lexer.
	 * @throws SmartScriptParserException if documentBody can not be parsed.
	 */
	private void parseDocument() {
		SmartScriptToken token;
		while (true) {
			try {
				token = lexer.nextToken();
			} catch (LexerException e) {
				throw new SmartScriptParserException();
			}
			
			if (token.getType().equals(SSTokenType.EOF)) {
				break;
			} else if (token.getType().equals(SSTokenType.SYMBOL)) {
				resolveSymbol(token);
			} else if (token.getType().equals(SSTokenType.STRING)) {
				resolveString(token);
			} 
		}
	}
	
	/**
	 * Makes new {@link TextNode} based on current token from lexer.
	 * 
	 * @param token current token with {@link ElementString} value
	 */
	private void resolveString(SmartScriptToken token) {
		if (stack.peek() instanceof Node && token.getValue() instanceof Element) {
			TextNode node = new TextNode(((Element)token.getValue()).asText());
			((Node)stack.peek()).addChildNode(node);
		} else {
			throw new SmartScriptParserException("Unexpected error has occured. "
					+ "Unable to parse document.");
		}
	}
	
	/**
	 * Makes node based on symbol for current token in lexer.
	 * 
	 * @param token current token in lexer.
	 * @throws SmartScriptParserException symbol not used properly or not exit.
	 */
	private void resolveSymbol(SmartScriptToken token) {
		lexer.setState(SSLexerState.TAG);
		try {
			token = lexer.nextToken();
		} catch (LexerException e) {
			throw new SmartScriptParserException();
		}
		
		if (token.getType().equals(SSTokenType.SYMBOL)) {
			if (token.getValue() instanceof Element && ((Element)token.getValue()).asText().equals("=")) {
				makeEchoNode();
			} else {
				throw new SmartScriptParserException("Unrecognized tag.");
			}
		} else if (token.getType().equals(SSTokenType.VAR)) {
			String forLoopSign = "FOR";
			String endOfForLoop = "END";
			if (token.getValue() instanceof Element && 
					((Element)token.getValue()).asText().equals(forLoopSign)){
				try {
					token = lexer.nextToken();
				} catch (LexerException e) {
					throw new SmartScriptParserException("Unable to read next from lexer.");
				}
				makeForLoopNode(token);
			} else if (token.getValue() instanceof Element &&
					((Element)token.getValue()).asText().equals(endOfForLoop)) {
				if (stack.size() > 1) {
					stack.pop();
				} else {
					throw new SmartScriptParserException("Tag END found, but tag never opened.");
				}
				try {
					token = lexer.nextToken();
				} catch (LexerException e) {
					throw new SmartScriptParserException("Unable to read next from lexer.");
				}
				
				if (!token.getType().equals(SSTokenType.SYMBOL) || !((Element)token.getValue()).asText().equals("$}")) {
					throw new SmartScriptParserException("After END : expected '$}'.") ;
				}
				lexer.setState(SSLexerState.TEXT);
				
			} else {
				throw new SmartScriptParserException("After {$: unexpected sign has occured.");
			}
		} else {
			throw new SmartScriptParserException("Unexpected error has occured with tag parts.");
		}
		
	}
	
	/**
	 * This method is called iff new loop node need to be initialized
	 * 
	 * @param token current token from lexer.
	 * @throws SmartScriptParserException if for loop not initialized properly
	 */
	private void makeForLoopNode(SmartScriptToken token) {
		lexer.setState(SSLexerState.TAG);
		ElementVariable elemVar;
		if (token.getValue() instanceof ElementVariable) {
			elemVar = (ElementVariable) token.getValue();
		} else {
			throw new SmartScriptParserException("Definition of for loop must start with variable.");
		}
		ArrayIndexedCollection elems = new ArrayIndexedCollection(3);
		for (int i = 0; i < 3; i++) {
			try {
				token = lexer.nextToken();
			} catch (LexerException e) {
				throw new SmartScriptParserException("Unable to read next from lexer.");
			}
			if (i == 2 && token.getType() == SSTokenType.SYMBOL) {
				break;
			}
			
			if (token.getType() != SSTokenType.INTEGER
					&& token.getType() != SSTokenType.DOUBLE
						&& token.getType() != SSTokenType.VAR
							&& token.getType() != SSTokenType.STRING) {
				throw new SmartScriptParserException("For loop not defined properly.");
			}
			elems.add(token.getValue());
		}
		if (token.getType() != SSTokenType.SYMBOL) {
			try {
				token = lexer.nextToken();
			} catch (LexerException e) {
				throw new SmartScriptParserException("Unable to read next from lexer.");
			}
			if (token.getType() != SSTokenType.SYMBOL) {
				throw new SmartScriptParserException("For loop not defined properly.");
			}
		}
		
		lexer.setState(SSLexerState.TEXT);
		
		if (stack.peek() instanceof Node &&  elems.size() == 2) {
			ForLoopNode refNode = new ForLoopNode(elemVar, (Element)elems.get(0), (Element)elems.get(1));
			((Node)stack.peek()).addChildNode(refNode);
			stack.push(refNode);
		} else if(stack.peek() instanceof Node && elems.size() == 3){
			ForLoopNode refNode = new ForLoopNode(elemVar, (Element)elems.get(0)
					,(Element)elems.get(1), (Element)elems.get(2));
			((Node)stack.peek()).addChildNode(refNode);
			stack.push(refNode);
		} else {
			throw new SmartScriptParserException("For loop not defined properly.");
		}
		
			
	}
	
	/**
	 * This method is called iff new echo node need to be initialized.
	 * @throws SmartScriptParserException if EchoNode not initialized properly
	 */
	private void makeEchoNode() {
		SmartScriptToken token;
		ArrayIndexedCollection elems = new ArrayIndexedCollection();
		
		lexer.setState(SSLexerState.TAG);
		do {
			try {
				token = lexer.nextToken();
			} catch (LexerException e) {
				throw new SmartScriptParserException("Unable to read next from lexer.");
			}
			if (token.getValue() instanceof Element && token.getType() != SSTokenType.SYMBOL) {
				elems.add((Element)token.getValue());
			}
			
		} while (token.getType() != SSTokenType.SYMBOL && 
				!((Element)token.getValue()).asText().equals("$}"));
		Element[] elements = new Element[elems.size()];
		for (int i = 0; i < elems.size(); i++) {
			elements[i] = (Element) elems.get(i);
		}
		if (stack.peek() instanceof Node) {
			((Node)stack.peek()).addChildNode(new EchoNode(elements));
		} else {
			throw new SmartScriptParserException("Unexpected error has occured.");
		}
		lexer.setState(SSLexerState.TEXT);
	}
	
	/**
	 * Gets and return document.
	 * 
	 * @return DocumentNode document
	 * @throws SmartScriptParserException if DocumentNode can not be found
	 */
	public DocumentNode getDocumentNode() {
		if ( stack.size() == 1 && stack.peek() instanceof DocumentNode )
			return (DocumentNode) stack.peek();
		
		throw new SmartScriptParserException("Parser cannot find DocumentNode.");
	}
}
