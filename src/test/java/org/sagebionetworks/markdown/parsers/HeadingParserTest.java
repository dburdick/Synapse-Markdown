package org.sagebionetworks.markdown.parsers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class HeadingParserTest {
	HeadingParser parser;
	
	@Before
	public void setup(){
		parser = new HeadingParser();
	}
	
	@Test
	public void testHeading(){
		String text = "Basic Heading";
		String line = "### " + text;
		MarkdownElements elements = new MarkdownElements(line);
		parser.processLine(elements);
		String result = elements.getHtml();
		assertTrue(result.contains("<h3>"));
		assertTrue(result.contains(text));
		assertTrue(result.contains("</h3>"));
	}
	
	@Test
	public void testHeadingInBlockquote(){
		String text = "Basic Heading";
		String line = "> ### " + text;
		MarkdownElements elements = new MarkdownElements(line);
		parser.processLine(elements);
		String result = elements.getHtml();
		assertTrue(result.contains("<h3>"));
		assertTrue(result.contains(text));
		assertTrue(result.contains("</h3>"));
	}
}
