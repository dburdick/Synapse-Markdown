package org.sagebionetworks.markdown.parsers;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sagebionetworks.markdown.constants.MarkdownRegExConstants;

public class SynapseAutoLinkParser extends BasicMarkdownElementParser {
	Pattern p = Pattern.compile(MarkdownRegExConstants.LINK_SYNAPSE);
	
	@Override
	public void processLine(MarkdownElements line) {
		Matcher m = p.matcher(line.getMarkdown());
		StringBuffer sb = new StringBuffer();
		while(m.find()) {
			String updated = "<a class=\"link\" href=\"#!Synapse:" + m.group(1) +"\">" + m.group(1) + "</a>";
			m.appendReplacement(sb, updated);
		}
		m.appendTail(sb);
		line.updateMarkdown(sb.toString());
	}

}
