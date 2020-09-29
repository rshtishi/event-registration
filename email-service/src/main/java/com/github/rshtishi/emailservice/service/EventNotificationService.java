package com.github.rshtishi.emailservice.service;

import com.github.rshtishi.emailservice.entity.EventSubscriber;

public interface EventNotificationService {

	public void notifyEventSubscriberByEmail(EventSubscriber subscriber);
}
