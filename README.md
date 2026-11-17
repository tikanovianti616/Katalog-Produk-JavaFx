# 🛍️ Katalog Produk — JavaFX 

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
```text

## Cara Menjalankan Aplikasi

1. **Clone Repository**
   Buka terminal atau Command Prompt, lalu jalankan perintah berikut:
   git clone https://github.com/tikanovianti616/Katalog-Produk-JavaFx.git

2. **Masuk ke Folder Project**
    Setelah proses clone selesai, arahkan terminal ke dalam folder proyek:
    cd Katalog-Produk-JavaFx

3. **Jalankan Aplikasi**
    Anda bisa menjalankan aplikasi ini melalui salah satu dari dua cara berikut:

    **Opsi A: Langsung Lewat Terminal**
    Pastikan terminal berada di dalam folder Katalog-Produk-JavaFx, lalu jalankan perintah:
    mvn clean javafx:run

    Tunggu hingga proses build selesai. Jika berhasil, jendela aplikasi Katalog Produk akan langsung terbuka dan siap digunakan.

    **Opsi B: Melalui Apache NetBeans**
    1) Buka aplikasi Apache NetBeans, lalu pilih File → Open Project dan pilih folder Katalog-Produk-JavaFx.
    2) Tunggu hingga proses pemuatan dependency Maven selesai.
    3) Atur konfigurasi Run pada NetBeans:
        - Klik kanan pada nama project (KatalogProduk) di panel kiri → pilih Properties.
        - Pilih menu Actions di sebelah kiri → klik Run project pada daftar Actions.
        - Pada kolom Execute Goals, ketik clean javafx:run (atau javafx:run), lalu klik OK.
    
4. Jalankan aplikasi dengan menekan tombol Run Project (ikon segitiga hijau / F6), atau klik kanan pada MainApp.java → Run File (Shift + F6).
    Catatan: Anda tidak perlu membuat database secara manual. File database lokal (data/katalog.db) akan otomatis terbentuk saat aplikasi pertama kali dijalankan.
