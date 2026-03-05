# Teknik Rapor: Unidev Not Uygulaması Geliştirme Süreci

Bu rapor, UNIDEV Yazılım için geliştirilen mobil not alma uygulamasının teknik detaylarını, mimarisini ve özelliklerini özetler.

## 1. Proje Özeti
Uygulama, kullanıcıların günlük notlarını dijital ortamda şık ve güvenli bir şekilde saklamalarını sağlayan Android tabanlı bir prototiptir.

## 2. Teknik Gereksinimler ve Teknoloji Yığını
- **Kotlin:** Modern ve güvenli kodlama için ana dil olarak seçilmiştir.
- **Jetpack Compose:** Deklaratif kullanıcı arayüzü ile hızlı ve modern bir UI sunulmuştur.
- **Room Database:** SQLite üzerine kurulu yerel depolama ile verilerin kalıcı olması sağlanmıştır.
- **MVVM Mimarisi:** Kodun okunabilirliği ve bakımı için Model-View-ViewModel deseni uygulanmıştır.

## 3. Uygulanan Temel Özellikler
- **Not Yönetimi:** Not ekleme, listeleme ve silme işlevleri.
- **Not Düzenleme:** Mevcut notların içeriği ve başlığı her an güncellenebilir.
- **Gelişmiş Kategori Yönetimi:** Kullanıcılar uygulama içinden yeni kategoriler ekleyebilir veya mevcut kategorileri silebilir. Uygulama, başlangıçta mükerrer (kopya) kategorileri otomatik olarak tespit eder ve temizler.
- **Kesin Türkçe Karakter Desteği:** Not giriş alanları, Android'in yerel metin işleme motoruyla tam uyumlu hale getirilmiş, karakter girişini engelleyebilecek karmaşık veri yapıları sadeleştirilmiştir. Bu sayede "ç, ş, ğ, İ" gibi karakterler tüm cihazlarda sorunsuz bir şekilde kullanılabilir. Mükemmel bir kullanıcı deneyimi için klavye ve yazı tipi ayarları optimize edilmiştir.

## 4. Kullanıcı Arayüzü (UI) Tasarımı
Arayüz, Material 3 standartlarında, modern ve sade bir dille tasarlanmıştır. 
- **Ana Ekran:** Kategorilere hızlı erişim sağlayan bir seçim çubuğu (Filter Chips) ve not kartları.
- **Detay/Ekleme Ekranı:** Yeni kategori ekleme diyaloğu ve interaktif kategori seçim alanı.

## 5. Mimari Detaylar
- **Data (Repository & DAO):** Veritabanı işlemleri soyutlanmış, asenkron (Coroutines) yapı ile performans artırılmıştır.
- **Gezinme (Navigation):** Compose Navigation kullanılarak ekranlar arası veri aktarımı ve geçiş yönetimi sağlanmıştır.

## 6. Sonuç
Proje, belirtilen tüm işlevsel ve teknik kriterleri karşılayacak şekilde, modern Android geliştirme standartlarına uygun olarak tamamlanmıştır.

---
**Hazırlayan:** Unidev Geliştirme Asistanı
**Tarih:** 21 Şubat 2026
ve Kategori verilerinin tanımlandığı katman (`Note.kt`, `Category.kt`).
- **Data Layer (Veri):** Room Database, DAO ve Repository deseninin kullanıldığı, verinin fiziksel olarak yönetildiği katman (`NoteDatabase.kt`, `NoteRepository.kt`).
- **UI Layer (Arayüz):** Jetpack Compose ekranlarının ve ViewModel'in bulunduğu, kullanıcının etkileşime girdiği katman.

## 4. Temel Özellikler
1. **Not Ekleme:** Kullanıcı başlık ve içerik girerek yeni notlar oluşturabilir.
3. **Not Silme:** Liste üzerinden tek dokunuşla notlar silinebilir.
4. **Ekranlar Arası Geçiş:** Navigation bileşeni sayesinde ekranlar arası kesintisiz geçiş sağlanır.

## 5. Kurulum Talimatları
1. Projeyi Android Studio'da açın.
2. `build.gradle` dosyasına `Room` ve `Navigation` kütüphanelerini ekleyin.
3. Uygulamayı bir emülatör veya gerçek bir cihaz üzerinde çalıştırın.

---
**Hazırlayan:** Antigravity AI (Öğretmen Modu)
**Tarih:** 21.02.2026
