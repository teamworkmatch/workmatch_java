
package id.project.workmatch.controller;

import id.project.workmatch.model.Lowongan;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Data {
    private static Data instance;
    private List<Lowongan> lowongans = new ArrayList<>();
    private int nextId = 1;

    private String adminUser = "admin";
    private String adminPass = "123";

    private Data() {
//         sample data
//        lowongans.add(new Lowongan(nextId++, "Programmer Java", "PT Contoh", "Jakarta", "Mengembangkan aplikasi desktop."));
//        lowongans.add(new Lowongan(nextId++, "Front-end Developer", "PT Web", "Bandung", "Membangun UI modern."));
    }

    public static Data getInstance() {
        if (instance == null) instance = new Data();
        return instance;
    }

    public boolean authenticate(String user, String pass) {
        return adminUser.equals(user) && adminPass.equals(pass);
    }

    public List<Lowongan> getAll() {
        return new ArrayList<>(lowongans);
    }

    public void add(String judul, String perusahaan, String lokasi, String deskripsi) {
        lowongans.add(new Lowongan(nextId++, judul, perusahaan, lokasi, deskripsi));
    }

    public boolean update(int id, String judul, String perusahaan, String lokasi, String deskripsi) {
        Optional<Lowongan> opt = lowongans.stream().filter(l -> l.getId()==id).findFirst();
        if (opt.isPresent()) {
            Lowongan l = opt.get();
            l.setJudul(judul); l.setPerusahaan(perusahaan); l.setLokasi(lokasi); l.setDeskripsi(deskripsi);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        return lowongans.removeIf(l -> l.getId()==id);
    }
}
