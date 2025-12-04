import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaction {
    private final ZonedDateTime timestamp;
    private final double amount;
    private final String description;
    private final String type;
    private final String counterpartyAgency;
    private final String counterpartyAccount;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'GMT'XXX");

    public Transaction(String type, double amount, String date, String description,
                       String counterpartyAgency, String counterpartyAccount, ZoneId zone) {
        ZonedDateTime t;
        if (date == null) {
            t = ZonedDateTime.now(zone);
        } else {
            try {
                Instant inst = Instant.parse(date);
                t = ZonedDateTime.ofInstant(inst, zone);
            } catch (DateTimeParseException e) {
                t = ZonedDateTime.now(zone);
            }
        }
        this.timestamp = t;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.counterpartyAgency = counterpartyAgency;
        this.counterpartyAccount = counterpartyAccount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCounterpartyAccount() {
        return counterpartyAccount;
    }

    public String getCounterpartyAgency() {
        return counterpartyAgency;
    }

    @Override
    public String toString() {
        return String.format("%s | %s",
                description,
                timestamp.format(FORMATTER));
    }
}