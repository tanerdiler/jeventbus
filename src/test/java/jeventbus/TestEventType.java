package jeventbus;

import jeventbus.shared.EventType;

public enum TestEventType implements EventType {
	VISITORLOGON("onVisitorLogon");

	private String method;
	
	private TestEventType(String method) {
		this.method =method;
	}
	public String getMethodName() {
		return method;
	}

}
