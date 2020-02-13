package Model;

public class CertificateModel {
    public String getOrganization() {
        return organization;
    }

    public CertificateModel setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertificateModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getPeriod() {
        return period;
    }

    public CertificateModel setPeriod(String period) {
        this.period = period;
        return this;
    }

    public String getTrade() {
        return trade;
    }

    public CertificateModel setTrade(String trade) {
        this.trade = trade;
        return this;
    }

    private String organization;
    private String name;
    private String period;
    private String trade;
}
