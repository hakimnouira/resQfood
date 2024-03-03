package services.siwar;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {
    private final String accountSid;
    private final String authToken;
    private final String twilioPhoneNumber;

    public SMSService(String accountSid, String authToken, String twilioPhoneNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.twilioPhoneNumber = twilioPhoneNumber;
    }

    // Initialize Twilio client
    public void initializeTwilio() {
        Twilio.init(accountSid, authToken);
    }

    // Send SMS notification
    public void sendSMS(String toPhoneNumber, String message) {
        try {
            Message twilioMessage = Message.creator(
                            new PhoneNumber(toPhoneNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            message)
                    .create();

            System.out.println("SMS sent successfully. SID: " + twilioMessage.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }
}
