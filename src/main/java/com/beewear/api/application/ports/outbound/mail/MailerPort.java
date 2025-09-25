package com.beewear.api.application.ports.outbound.mail;

import java.util.Map;

public interface MailerPort {
    void sendEmail(String to, String subject, String templateName, Map<String, Object> variables);
}
