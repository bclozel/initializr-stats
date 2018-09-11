package io.spring.sample.dashboard.stats.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReverseLookupDescriptor {

	private final String ip;
	private final String domainName;

	@JsonCreator
	ReverseLookupDescriptor(@JsonProperty("ip") String ip, @JsonProperty("domainName") String domainName) {
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
