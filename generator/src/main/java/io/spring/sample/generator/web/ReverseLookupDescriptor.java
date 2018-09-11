package io.spring.sample.generator.web;

class ReverseLookupDescriptor {

	private final String ip;
	private final String domainName;

	ReverseLookupDescriptor(String ip, String domainName) {
		this.ip = ip;
		this.domainName = domainName;
	}

	public String getIp() {
		return this.ip;
	}

	public String getDomainName() {
		return this.domainName;
	}

}
