/**
 * 
 */
package hr.fer.zemris.java.hw3;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * @author Stjepan
 *
 */
public class SmartScriptTester {
	
	public static void main (String[] args) {
		String docBody = null;
		try {
			// read all from file
			docBody = new String(
					Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SmartScriptParser parser = null;
		// Parse text from file
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		// gets document node, should never throw here
		DocumentNode document = parser.getDocumentNode();
		// creates original text
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
												  // content of docBody
		
		System.out.println("\n___________________________________________________");
		
		// now lets check if everything works if generate with new text
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println(createOriginalDocumentBody(document2)); 
				// should print same as first time
				// document and document2 should be structurally identical trees
		
	}

	private static String createOriginalDocumentBody(DocumentNode document) {
		return document.toString();//generates original text
	}
	
}
