package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import data.ProdukRepository;
import model.Produk;

public class KatalogController {

    @FXML
    private TextField namaField;
    @FXML
    private TextField hargaField;
    @FXML
    private ComboBox<String> fotoBox;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;
    @FXML
    private Label pesanLabel;
    @FXML
    private Label jumlahLabel;
    @FXML
    private TilePane katalogPane;

    private final ProdukRepository repository = new ProdukRepository();
    private Produk produkSedangDiedit = null;

    @FXML
    private void initialize() {
        fotoBox.getItems().add("roti.png");
        fotoBox.getItems().add("susu.png");
        fotoBox.getItems().add("kopi.png");
        fotoBox.setValue("roti.png");
        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        ArrayList<Produk> daftar = repository.getAll();
        katalogPane.getChildren().clear();
        for (Produk produk : daftar) {
            VBox kartu = createProductCard(produk);
            katalogPane.getChildren().add(kartu);
        }
        jumlahLabel.setText("Jumlah produk: " + daftar.size());
    }

    private VBox createProductCard(Produk produk) {
        ImageView fotoView = new ImageView();
        fotoView.setFitWidth(170);
        fotoView.setFitHeight(110);
        fotoView.setPreserveRatio(true);
        StackPane bingkai = new StackPane();
        bingkai.setPrefSize(180, 120);
        bingkai.setMinSize(180, 120);
        bingkai.setMaxSize(180, 120);

        URL lokasi = getClass().getResource("/image/" + produk.getFoto());
        if (lokasi != null) {
            Image gambar = new Image(lokasi.toExternalForm(),
                    170, 110, true, true);
            if (!gambar.isError()) {
                fotoView.setImage(gambar);
                bingkai.getChildren().add(fotoView);
            } else {
                bingkai.getChildren().add(new Label("Gambar tidak terbaca"));
            }
        } else {
            bingkai.getChildren().add(new Label("Gambar tidak tersedia"));
        }

        Label namaLabel = new Label(produk.getNama());
        namaLabel.setWrapText(true);
        namaLabel.getStyleClass().add("nama-produk");
        Label hargaLabel = new Label("Rp" + produk.getHarga());
        Label idLabel = new Label("ID: " + produk.getId());
        idLabel.getStyleClass().add("muted");

        // Tombol Edit & Hapus di setiap kartu produk
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> mulaiEdit(produk));

        Button hapusButton = new Button("Hapus");
        hapusButton.setOnAction(e -> hapusProduk(produk));

        HBox aksiBox = new HBox(8, editButton, hapusButton);

        VBox kartu = new VBox();
        kartu.setSpacing(8);
        kartu.setMinWidth(220);
        kartu.setPrefWidth(220);
        kartu.setMaxWidth(220);
        kartu.getStyleClass().add("kartu-produk");
        kartu.getChildren().add(bingkai);
        kartu.getChildren().add(namaLabel);
        kartu.getChildren().add(hargaLabel);
        kartu.getChildren().add(idLabel);
        kartu.getChildren().add(aksiBox);
        return kartu;
    }

    private void mulaiEdit(Produk produk) {
        produkSedangDiedit = produk;
        namaField.setText(produk.getNama());
        hargaField.setText(String.valueOf(produk.getHarga()));
        fotoBox.setValue(produk.getFoto());

        simpanButton.setText("Update");
        batalButton.setVisible(true);
        batalButton.setManaged(true);
        pesanLabel.setText("Mengedit produk ID: " + produk.getId());
    }

    @FXML
    private void batalEdit() {
        resetForm();
        pesanLabel.setText("Edit dibatalkan.");
    }

    private void resetForm() {
        produkSedangDiedit = null;
        namaField.clear();
        hargaField.clear();
        fotoBox.setValue("roti.png");

        simpanButton.setText("Simpan");
        batalButton.setVisible(false);
        batalButton.setManaged(false);
    }

    private void hapusProduk(Produk produk) {
        Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
        konfirmasi.setTitle("Konfirmasi Hapus");
        konfirmasi.setHeaderText("Hapus produk \"" + produk.getNama() + "\"?");
        konfirmasi.setContentText("Data yang dihapus tidak dapat dikembalikan.");

        Optional<ButtonType> hasil = konfirmasi.showAndWait();
        if (hasil.isPresent() && hasil.get() == ButtonType.OK) {
            try {
                repository.delete(produk.getId());
                if (produkSedangDiedit != null && produkSedangDiedit.getId() == produk.getId()) {
                    resetForm();
                }
                showData();
                pesanLabel.setText("Produk berhasil dihapus.");
            } catch (SQLException e) {
                pesanLabel.setText("Gagal menghapus produk. Lihat Output.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveProduct() {
        String nama = namaField.getText();
        long harga;
        String foto = fotoBox.getValue();

        try {
            harga = Long.parseLong(hargaField.getText().trim());
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus angka bulat, contoh 12000.");
            return;
        }

        try {
            if (produkSedangDiedit == null) {
                // MODE TAMBAH BARU
                Produk produkBaru = new Produk(nama, harga, foto);
                repository.add(produkBaru);
                resetForm();
                showData();
                pesanLabel.setText("Produk berhasil disimpan.");
            } else {
                // MODE UPDATE / EDIT
                Produk produkUpdate = new Produk(produkSedangDiedit.getId(), nama, harga, foto);
                repository.update(produkUpdate);
                resetForm();
                showData();
                pesanLabel.setText("Produk berhasil diperbarui.");
            }
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menyimpan ke database. Lihat Output.");
            e.printStackTrace();
        }
    }
}