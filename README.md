# WorkMatch

Aplikasi manajemen lowongan kerja berbasis **Java Desktop (Swing)** dengan antarmuka modern dan fitur ekspor ke Excel.

---

## Deskripsi

**WorkMatch** adalah aplikasi desktop ringan yang dirancang untuk mengelola data lowongan kerja secara efisien. Dibangun menggunakan Java Swing dengan desain UI modern bergaya gradient, aplikasi ini cocok digunakan sebagai sistem manajemen lowongan internal perusahaan maupun proyek pembelajaran.

---

## Fitur

| Fitur | Deskripsi |
|---|---|
| Login | Autentikasi pengguna dengan username & password |
| Tambah Lowongan | Input data lowongan baru (Judul, Perusahaan, Lokasi, Deskripsi) |
| Edit Lowongan | Ubah data lowongan yang sudah ada |
| Hapus Lowongan | Hapus data lowongan dari daftar |
| Ekspor Excel | Export seluruh data lowongan ke file `.xlsx` via Apache POI |
| UI Modern | Tampilan gradient, tombol berwarna, dan tabel interaktif |

---

## Arsitektur Proyek

```
WorkMatch/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── id/project/workmatch/
                ├── Main.java                    # Entry point aplikasi
                ├── controller/
                │   └── Data.java               # Singleton controller + in-memory store
                ├── model/
                │   └── Lowongan.java           # Model data lowongan
                └── view/
                    ├── MasukFrame.java         # Tampilan halaman login
                    └── LowonganFrame.java      # Tampilan CRUD & tabel lowongan
```

### Pola Desain

- **MVC (Model-View-Controller)** — pemisahan antara data, logika, dan tampilan
- **Singleton Pattern** — `Data.java` menggunakan pola Singleton untuk satu instansi data global
- **In-Memory Storage** — data disimpan menggunakan `ArrayList` selama sesi berjalan

---

## Teknologi & Dependency

| Komponen | Detail |
|---|---|
| Bahasa | Java 23+ |
| UI Framework | Java Swing |
| Build Tool | Apache Maven |
| Ekspor Excel | Apache POI `poi-ooxml` v5.3.0 |
| Compiler Target | Java Release 23 |

---

## Prasyarat

Sebelum menjalankan aplikasi, pastikan sudah terinstal:

- JDK 23+ (direkomendasikan JDK 25)
- Apache Maven 3.6+
- IDE NetBeans / IntelliJ IDEA / Eclipse (opsional)

---

## Cara Menjalankan

### Menggunakan NetBeans

1. Buka NetBeans
2. `File` -> `Open Project` -> pilih folder `WorkMatch` (yang berisi `pom.xml`)
3. Klik kanan project -> `Clean and Build`
4. Klik kanan project -> `Run Project`

### Menggunakan Maven (Terminal)

```bash
# Clone repository
git clone https://github.com/username/workmatch_java.git
cd workmatch_java

# Build project
mvn clean package

# Jalankan aplikasi
mvn exec:java
```

---

## Kredensial Login Default

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `123` |

> **Catatan:** Kredensial ini bersifat hardcoded untuk keperluan demo. Tidak disarankan untuk lingkungan produksi.

---

## Alur Aplikasi

```
[Mulai]
   |
   v
[MasukFrame]  <-- Login dengan admin/123
   |
   v (Login berhasil)
[LowonganFrame]
   |-- Tambah Lowongan
   |-- Edit Lowongan
   |-- Hapus Lowongan
   └-- Export ke Excel (.xlsx)
```

---

## Data Lowongan

Setiap lowongan memiliki atribut berikut:

| Field | Tipe | Keterangan |
|---|---|---|
| `id` | `int` | ID unik auto-increment |
| `judul` | `String` | Judul posisi pekerjaan |
| `perusahaan` | `String` | Nama perusahaan |
| `lokasi` | `String` | Kota / lokasi kerja |
| `deskripsi` | `String` | Deskripsi singkat pekerjaan |

---

## Catatan Pengembangan

- Data hanya tersimpan **sementara di memori (RAM)** dan akan hilang saat aplikasi ditutup
- Untuk persistensi data, dapat dikembangkan dengan integrasi database (SQLite / MySQL via JDBC)
- Export Excel menghasilkan file `.xlsx` di direktori yang dipilih pengguna

---

## Referensi

- [Dokumentasi Teknis WorkMatch](https://workmatch.my.id/index.php?hal=post&post=workmatch-dokumentasi-teknis-aplikasi-manajemen-lowongan-kerja)
- [Apache POI Documentation](https://poi.apache.org/)
- [Java Swing Guide](https://docs.oracle.com/javase/tutorial/uiswing/)

---
