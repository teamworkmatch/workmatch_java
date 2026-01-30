
package id.project.workmatch.model;

public class Lowongan {
    private int id;
    private String judul;
    private String perusahaan;
    private String lokasi;
    private String deskripsi;

    public Lowongan(int id, String judul, String perusahaan, String lokasi, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.perusahaan = perusahaan;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getPerusahaan() { return perusahaan; }
    public String getLokasi() { return lokasi; }
    public String getDeskripsi() { return deskripsi; }

    public void setJudul(String judul) { this.judul = judul; }
    public void setPerusahaan(String perusahaan) { this.perusahaan = perusahaan; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}
