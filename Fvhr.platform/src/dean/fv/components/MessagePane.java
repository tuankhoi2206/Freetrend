package fv.components;

import nextapp.echo2.app.Column;
import echopointng.DirectHtml;

public class MessagePane extends Column {
	private static final long serialVersionUID = 1L;

	public static final String MSG_SUCCESS = "success";

	public static final String MSG_INFO = "info";

	public static final String MSG_WARNING = "warning";

	public static final String MSG_ERROR = "error";

	private DirectHtml html;

	public MessagePane() {
		super();
		html = new DirectHtml("<div></div>");
		add(html);
	}

	public void addMessage(String message, String messageType) {
		StringBuffer sb = new StringBuffer(html.getText());
		if (messageType.equals(MSG_SUCCESS)) {
			sb.append("<div style=\"color: #00CC00\">").append(
					messageTitle(messageType)).append(message).append("</div>");
		} else if (messageType.equals(MSG_INFO)) {
			sb.append("<div style=\"color: #007FFF\">").append(
					messageTitle(messageType)).append(message).append("</div>");
		} else if (messageType.equals(MSG_WARNING)) {
			sb.append("<div style=\"color: #FF7F00\">").append(
					messageTitle(messageType)).append(message).append("</div>");
		} else {
			sb.append("<div style=\"color: red\">").append(
					messageTitle(messageType)).append(message).append("</div>");
		}
		html.setText(sb.toString());
		firePropertyChange(null, null, null);
	}

	private String messageTitle(String messageType) {
		if (messageType.equals(MSG_SUCCESS))
			return "INFO: ";
		else
			return messageType.toUpperCase() + ": ";
	}

	public void clearMessage() {
		html.setText("<div></div>");
		firePropertyChange(null, null, null);
	}
}
