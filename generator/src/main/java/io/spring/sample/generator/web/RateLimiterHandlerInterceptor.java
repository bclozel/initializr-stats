package io.spring.sample.generator.web;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.bucket4j.Bucket;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

class RateLimiterHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final String SESSION_BUCKET_ATTRIBUTE = "RateLimiterBucket";

	private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

	private final Function<String, Bucket> bucketFactory;

	RateLimiterHandlerInterceptor(Function<String, Bucket> bucketFactory) {
		this.bucketFactory = bucketFactory;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) {
		String remoteHost = request.getRemoteHost();
		Bucket bucket = buckets.computeIfAbsent(remoteHost, this.bucketFactory);
		request.setAttribute(SESSION_BUCKET_ATTRIBUTE, bucket);
		return true;
	}

}
