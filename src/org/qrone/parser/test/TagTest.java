package org.qrone.parser.test;

import org.qrone.parser.CompileTagLib;
import org.qrone.parser.QrONEParser;
import org.qrone.parser.Tag;

import junit.framework.TestCase;

public class TagTest extends TestCase {

	public void testMath(){
		assertEquals("abMATHDATA!!ab",
				compile("ab<math>x^2</math>ab"));
		
	}
	
	public void testDif(){
		assertEquals("\n<ul type=\"square\">\n<li>test1</li>\n<li>test2</li>\n</ul>",
				compile("\n*test1\n*test2"));
		

		assertEquals("[ab",
				compile("[ab"));

		assertEquals("test<a href=\"ab\">ab</a>",
				compile("test[ab]"));

		assertEquals("test<a href=\"[ab\">[ab</a>",
				compile("test[[ab]"));

		assertEquals("<a href=\"ab\">ab</a>abc",
				compile("[[ab]]abc"));

		assertEquals("<a href=\"http://www.google.com/search?hl=ja&lr=lang_ja&ie=UTF-8&oe=UTF-8&q=ab&num=50\">Google: ab</a>",
				compile("[[google:ab]]"));
		
		assertEquals("ab<br/>\nab<br/>\nab<br/>\nab",
				compile("ab\nab\nab\nab"));

		assertEquals("ab<br/>\n<br/>\n\n<h3>abcde</h3>",
				compile("ab\n\n\n===abcde==="));

		assertEquals("<h1>ab</h1>\n\n<h3>abcde</h3>",
				compile("=ab=\n\n===abcde==="));

		assertEquals("<h1>ab</h1>\n\n<ul type=\"square\">\n<li>test1</li>\n<li>test2\n<ul type=\"square\">\n<li>test3</li>\n</ul></li>\n</ul>",
				compile("=ab=\n\n*test1\n*test2\n**test3"));		
	}
	
	public void testNow(){
		assertEquals("ab<br/>\n<br/>\nabc<br/>\n\n<h3>abcde</h3>",
				compile("ab\n\nabc\n\n===abcde==="));
	}

	public void testWiki(){
		assertEquals("<h3>abcde</h3>",
				compile("===abcde==="));

		assertEquals("ab===abcde===",
				compile("ab===abcde==="));

		assertEquals("ab\n<h3>abcde</h3>",
				compile("ab\n===abcde==="));

		assertEquals("ab<br/>\n=abc=\n<h2>abcde<ins>=</ins></h2>",
				compile("ab\n\\=abc=\n==abcde==="));

		assertEquals("ab\n<h3>abcde</h3>\ntest<br/>\ntest",
				compile("ab\n===abcde===\n\ntest\ntest"));

		assertEquals("ab\n<h3>abcde<ins>test</ins></h3>\ntest<br/>\ntest",
				compile("ab\n===abcde===test\n\ntest\ntest"));

		assertEquals("ab\n<h3>abcde</h3>\ntest<br/>\ntest",
				compile("ab\n===abcde===\ntest\ntest"));

		assertEquals("ab\n<h1>abcde</h1>\ntest",
				compile("ab\n=abcde=\ntest"));

		assertEquals("ab<br/>\n\n<h3>abcde</h3>",
				compile("ab\n\n===abcde==="));
		
		assertEquals("ab<br/>\nabc<em>abcde</em>ete",
				compile("ab\nabc{{{abcde}}}ete"));

		assertEquals("ab\n<ul type=\"square\">\n<li>abc</li>\n<li>te</li>\n</ul>",
				compile("ab\n*abc\n*te"));
		

		assertEquals("ab<br/>\n\n<ul type=\"square\">\n<li>abc</li>\n<li>te</li>\n</ul>\ntest",
				compile("ab\n\n*abc\n*te\n\ntest"));
	}
	
	public void testTest(){
		assertEquals("abcde",
				compile("abcde"));
		
		assertEquals("ab<br/>\ncde",
				compile("ab\ncde"));

		assertEquals("abc&lt;de",
				compile("abc<de"));
		
		assertEquals("abc&lt;abc&gt;de",
				compile("abc<abc>de"));
		
		assertEquals("abc<a>de</a>",
				compile("abc<a bc>de"));

		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a href=\"test\">de"));

		assertEquals("abc<a href=\"te<st\">de</a>",
				compile("abc<a href=\"te<st\">de"));
		
		assertEquals("abc<a href=\"te>st\">de</a>",
				compile("abc<a href=\"te>st\">de"));
		
		assertEquals("abc<a href=\"te\\\"st\">de</a>",
				compile("abc<a href=\"te\\\"st\">de"));

		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a href='test'>de"));

		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a a='t' href='test'>de"));

		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a a href='test'>de"));
		
		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a href=test>de"));
		
		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a href=test a=te>de"));

		assertEquals("abc<a href=\"test\">de</a>",
				compile("abc<a a=test href=test>de"));
		
		assertEquals("abc<a href=\"te\">st&gt;de</a>",
				compile("abc<a a href=te>st>de"));
		

		assertEquals("abc日本語テスト",
				compile("abc日本語テスト"));
	}
	
	public String compile(String str){
		QrONEParser tag= new QrONEParser(str);
		tag.addTagLib("math",new CompileTagLib(){
			public String bodyTag(Tag t, String content) {
				return "MATHDATA!!";
			}

			public String startTag(Tag t, boolean started) {
				return "";
			}

			public String endTag(Tag t) {
				return "";
			}

			public boolean isBlock() {
				return false;
			}});
		
		tag.parse();
		String s = tag.output();
		return s.substring(24,s.length()-7);
	}
}
