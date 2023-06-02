package com.officelunch;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;

import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;


public class Test {

    public static void main(String[] args) {
//        String connectionString = "endpoint=https://emai-l.communication.azure.com/;accesskey=32y5gtfKtIQ7xOu2U/ZezDMfA03qjJCL4ExLZadlqg0vp2gh6eozVuaNCoLGI737v9IBrXDo3hkBCRZYCFKadw==";
        String connectionString = "endpoint=https://access-systems-email-service.communication.azure.com/;accesskey=YTjD3GNf4KLCccHYPZdmeL6ONFgdpBEFalrL54WsLMuORTVMgzDOyDI+pzUDb8N93DLK99V86cmD8p6hIajKSA==";

        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        String subject = "hellL hellA hellD hellO";

        EmailMessage message = new EmailMessage()
                .setSenderAddress("DoNotReply@ab1173d1-af9e-43c5-9cec-908c03d18133.azurecomm.net")
                .setToRecipients("np03a190178@heraldcollege.edu.np")
                .setSubject("welcome to Azure Communication service email")
                .setBodyPlainText("email Ayo!!! ");

        SyncPoller<EmailSendResult,EmailSendResult> poller = emailClient.beginSend(message,null);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();

    }


}
