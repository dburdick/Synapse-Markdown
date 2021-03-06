package org.sagebionetworks.markdown.parsers;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.sagebionetworks.markdown.constants.WidgetConstants;
import org.sagebionetworks.markdown.constants.MarkdownRegExConstants;
import org.sagebionetworks.markdown.utils.ServerMarkdownUtils;
import org.sagebionetworks.markdown.utils.SharedMarkdownUtils;

public class CodeSpanParser extends BasicMarkdownElementParser {
	Pattern p1 = Pattern.compile(MarkdownRegExConstants.CODE_SPAN_REGEX);;
	MarkdownExtractor extractor;

	@Override
	public void reset(List<MarkdownElementParser> simpleParsers) {
		extractor = new MarkdownExtractor();
	}
	
	private String getCurrentDivID() {
		return WidgetConstants.DIV_ID_CODE_SPAN_PREFIX + extractor.getCurrentContainerId() + SharedMarkdownUtils.getPreviewSuffix(isPreview);
	}
	
	@Override
	public void processLine(MarkdownElements line) {
		Matcher m = p1.matcher(line.getMarkdown());
		StringBuffer sb = new StringBuffer();
		while(m.find()) {
			StringBuilder html = new StringBuilder();
			html.append("<code>");
			html.append(m.group(2));
			html.append("</code>");
			extractor.putContainerIdToContent(getCurrentDivID(), html.toString());
			
			String containerElement = extractor.getNewElementStart(getCurrentDivID()) + extractor.getContainerElementEnd();
			m.appendReplacement(sb, containerElement);
		}
		m.appendTail(sb);
		line.updateMarkdown(sb.toString());
	}
	
	@Override
	public void completeParse(Document doc) {
		ServerMarkdownUtils.insertExtractedContentToMarkdown(extractor, doc, true);
	}
}
