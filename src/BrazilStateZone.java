import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum BrazilStateZone {
    AC("AC", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-5))),

    AM("AM", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4))),
    RR("RR", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4))),
    RO("RO", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4))),
    PA("PA", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4))),
    MT("MT", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4))),
    MS("MS", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4))),

    AP("AP", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    MA("MA", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    TO("TO", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    PI("PI", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    CE("CE", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    RN("RN", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    PB("PB", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    PE("PE", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    AL("AL", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    SE("SE", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    BA("BA", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    DF("DF", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    GO("GO", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    MG("MG", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    ES("ES", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    RJ("RJ", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    SP("SP", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    PR("PR", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    SC("SC", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3))),
    RS("RS", ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3)));

    private final String uf;
    private final ZoneId zone;

    private static final Map<String, BrazilStateZone> MAP = new HashMap<>();

    static {
        for (BrazilStateZone b : values()) {
            MAP.put(b.uf, b);
        }
    }

    BrazilStateZone(String uf, ZoneId zone) {
        this.uf = uf;
        this.zone = zone;
    }

    public ZoneId getZoneId() {
        return zone;
    }

    public static BrazilStateZone fromUf(String uf) {
        if (uf == null) return null;
        return MAP.get(uf.toUpperCase(Locale.ROOT));
    }

    public static ZoneId zoneForUf(String uf) {
        BrazilStateZone b = fromUf(uf);
        return (b != null) ? b.getZoneId() : ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-3));
    }
}
