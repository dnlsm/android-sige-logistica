package br.com.cpqd.instrumentacao.sige.models;

public class Protocol {

    private int protocol_code;
    private String protocol_name;
    private String protocol_client;
    private String protocol_glab_code;
    private String protocol_sq_code;
    private String protocol_cpqdic_code;
    private String protocol_photos_url;
    private String protocol_notes;
    private String protocol_invoice;


    public Protocol(int protocol_code, String protocol_name, String protocol_client, String protocol_glab_code,
                    String protocol_sq_code, String protocol_cpqdic_code, String protocol_photos_url, String protocol_invoice, String protocol_notes) {
        this.protocol_code = protocol_code;
        this.protocol_name = protocol_name;
        this.protocol_client = protocol_client;
        this.protocol_glab_code = protocol_glab_code;
        this.protocol_sq_code = protocol_sq_code;
        this.protocol_cpqdic_code = protocol_cpqdic_code;
        this.protocol_photos_url = protocol_photos_url;
        this.protocol_invoice = protocol_invoice;
        this.protocol_notes = protocol_notes;
    }

    public String getProtocol_invoice() {
        return protocol_invoice;
    }

    public void setProtocol_invoice(String protocol_invoice) {
        this.protocol_invoice = protocol_invoice;
    }

    public String getProtocol_notes() {
        return protocol_notes;
    }

    public void setProtocol_notes(String protocol_notes) {
        this.protocol_notes = protocol_notes;
    }

    public String getProtocol_photos_url() {
        return protocol_photos_url;
    }

    public void setProtocol_photos_url(String protocol_photos_url) {
        this.protocol_photos_url = protocol_photos_url;
    }

    public String getProtocol_cpqdic_code() {
        return protocol_cpqdic_code;
    }

    public void setProtocol_cpqdic_code(String protocol_cpqdic_code) {
        this.protocol_cpqdic_code = protocol_cpqdic_code;
    }

    public String getProtocol_sq_code() {
        return protocol_sq_code;
    }

    public void setProtocol_sq_code(String protocol_sq_code) {
        this.protocol_sq_code = protocol_sq_code;
    }

    public String getProtocol_glab_code() {
        return protocol_glab_code;
    }

    public void setProtocol_glab_code(String protocol_glab_code) {
        this.protocol_glab_code = protocol_glab_code;
    }

    public String getProtocol_client() {
        return protocol_client;
    }

    public void setProtocol_client(String protocol_client) {
        this.protocol_client = protocol_client;
    }

    public String getProtocol_name() {
        return protocol_name;
    }

    public void setProtocol_name(String protocol_name) {
        this.protocol_name = protocol_name;
    }

    public int getProtocol_code() {
        return protocol_code;
    }

    public void setProtocol_code(int protocol_code) {
        this.protocol_code = protocol_code;
    }
}
