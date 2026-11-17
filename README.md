# 🛍️ Katalog Produk UPI Store — JavaFX GUI (Level 3)

![Java](https://img.shields.io/badge/Java-JDK%2026-orange?style=for-the-badge&logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?style=for-the-badge)
![SQLite](https://img.shields.io/badge/SQLite-3.49-003B57?style=for-the-badge&logo=sqlite)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge)

> Aplikasi desktop manajemen katalog produk berbasis **JavaFX** dan **SQLite** dengan antarmuka modern berbasis kartu (*card layout*) serta dukungan operasi **CRUD** (*Create, Read, Update, Delete*) secara penuh.

---

## 👤 Identitas Mahasiswa
* **Nama:** Tika Novianti
* **NIM:** 2508157
* **Topik:** Project Pengantar JavaFX GUI — Level 3

---

## ✨ Fitur Utama

| Fitur | Operasi | Deskripsi |
| :--- | :---: | :--- |
| **Tambah Produk** | `Create` | Input nama produk, harga (Rp), dan pilihan gambar melalui dropdown, dilengkapi validasi input sebelum disimpan ke SQLite. |
| **Katalog Dinamis** | `Read` | Menampilkan daftar produk secara otomatis dalam grid kartu (`TilePane`) lengkap dengan gambar, harga, ID, dan penghitung total produk. |
| **Edit Produk** | `Update` | Mengklik tombol **Edit** pada kartu akan memuat data produk ke form atas, mengubah tombol menjadi **Update**, dan menyediakan tombol **Batal**. |
| **Hapus Produk** | `Delete` | Menghapus produk yang dipilih langsung dari tampilan katalog maupun database SQLite secara real-time. |
| **Auto-Database** | `Setup` | Membuat folder `data/` dan tabel `produk` pada SQLite secara otomatis saat aplikasi pertama kali dijalankan. |

---

## 🏗️ Arsitektur & Struktur Folder `src`

Aplikasi ini menerapkan pemisahan tanggung jawab (*Separation of Concerns*) antara tampilan, logika kontrol, akses database, dan model data:

```text
src/
└── main/
    ├── java/
    │   ├── controller/
    │   │   └── KatalogController.java   # Mengatur event UI, validasi, mode edit, & render kartu
    │   ├── data/
    │   │   └── ProdukRepository.java    # Layer akses data SQLite (insert, findAll, update, delete)
    │   ├── db/
    │   │   └── Database.java            # Manajemen koneksi JDBC SQLite & inisialisasi tabel
    │   ├── main/
    │   │   └── MainApp.java             # Entry point aplikasi JavaFX
    │   └── model/
    │       └── Produk.java              # Model entitas Produk (id, nama, harga, foto)
    └── resources/
        ├── image/
        │   ├── kopi.png                 # Aset gambar produk
        │   ├── roti.png
        │   └── susu.png
        └── view/
            ├── katalog.fxml             # Struktur layout GUI (Scene Builder)
            └── styles/
                └── app.css              # Styling kartu produk, tombol, dan tipografi
