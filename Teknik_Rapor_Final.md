# Unidev Not Uygulaması - Teknik Rapor ve Prototip Özeti

## 1. Proje Hakkında
Unidev Not, modern Android standartlarını (Jetpack Compose, Material 3, Room Database) kullanan, kategorize edilebilir ve kullanıcı dostu bir not tutma uygulamasıdır. Proje, özellikle kullanıcı deneyimi, veri güvenliği ve evrensel dil desteği (Türkçe karakter) odaklı geliştirilmiştir.

## 2. Kullanılan Teknolojiler
- **Dil:** Kotlin (1.9.22)
- **UI Framework:** Jetpack Compose (Modern, deklaratif kullanıcı arayüzü)
- **Veritabanı:** Room Persistence Library (Yerel veri saklama)
- **Mimari:** MVVM (Model-View-ViewModel) & Repository Pattern
- **Tasarım:** Material 3 (Material Design'ın son sürümü)
- **Navigasyon:** Jetpack Navigation Compose

## 3. Geliştirme Aşamaları ve Çözülen Temel Problemler
Proje süreci, basit bir prototipten profesyonel bir ürüne kadar şu aşamalardan geçmiştir:
- **Veri Modeli ve Veritabanı:** Notlar ve kategoriler için Room veritabanı iskeleti kuruldu.
- **UI Tasarımı:** Kullanıcının notlarını listeleyebileceği, kategorilere göre filtreleyebileceği ve yeni notlar ekleyebileceği modern bir Material 3 arayüzü tasarlandı.
- **Karakter Uyumluluğu:** Bazı emülatörlerde yaşanan Türkçe karakter (ı, ş, İ) giriş sorunları, `TextFieldValue` ve `Locale` seviyesindeki derin teknik müdahalelerle çözüldü.
- **Cihaz Uyumluluğu (API 26-36):** Eski cihazlarda (Android 8) ve en yeni cihazlarda (Android 15+) yaşanan siyah ekran ve tema uyuşmazlıkları, `enableEdgeToEdge` ve özelleştirilmiş Material 3 şemaları ile giderildi.

## 4. Uygulama Özellikleri
- **Not Yönetimi:** Not ekleme, düzenleme ve silme.
- **Kategori Sistemi:** Notları İş, Kişisel, Fikirler gibi renkli kategorilere ayırma ve dinamik kategori ekleme/silme.
- **Filtreleme:** Kategorilere göre anlık not listesi filtreleme.
- **Modern Tasarım:** Karanlık mod (Dark Mode) desteği ve dinamik renk uyumu.

## 5. Kurulum ve Çalıştırma
Kaynak kodlar Android Studio üzerinden açılıp `Sync Project with Gradle Files` yapıldıktan sonra direkt olarak çalıştırılabilir. Minimum SDK sürümü 26 (Android 8.0) olarak belirlenmiştir.

---
**Geliştirici:** Unidev Proje Ekibi / Prototip Sürümü
**Tarih:** 22.02.2026
