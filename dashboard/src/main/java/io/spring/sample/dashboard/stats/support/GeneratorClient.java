package io.spring.sample.dashboard.stats.support;

public class GeneratorClient {

	private String ip;

	public GeneratorClient() {
	}

	public GeneratorClient(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
