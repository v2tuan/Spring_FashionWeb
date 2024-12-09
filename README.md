# Hệ Thống Quản Lý Cửa Hàng Quần Áo FASHIONISTA - Fashion With Passion

## Tổng Quan
Trang web Fashionista là môt dự án nhóm được thiết kế để quản lý cửa hàng quần áo một cách hiệu quả. Dự án này được xây dựng bằng Spring Boot, Thymeleaf, Bootstrap, JPA, MySQL, và JWT để xác thực. Hệ thống bao gồm các tính năng quản lý sản phẩm, đơn hàng, người dùng và nhiều hơn nữa, hỗ trợ cả chức năng cho người quản trị (ADMIN), người quản lý (MANAGER) và người dùng (USER).

---

## Các Tính Năng Chính

### Chức Năng Chung
- **Đăng ký, Đăng nhập và Quên mật khẩu**
- **Xem danh sách các Sản phẩm**

### Dành Cho Khách Hàng
- **Chỉnh sửa thông tin Tài khoản cá nhân**
- **Đặt hàng**
- **Trạng thái Đơn hàng và Lịch sử Đơn hàng**
- **Đánh giá Sản phẩm đã mua**

### Dành Cho Quản Lý Viên
- **Cập nhật trạng thái Đơn hàng**
- **Cập nhật trạng thái Sản phẩm**

### Dành Cho Quản Trị Viên
- **Quản lý Sản phẩm**
- **Quản lý Hãng**
- **Quản lý Đơn hàng**
- **Quản Lý Người Dùng**

---

## Công Nghệ Sử Dụng

- **Backend:** Spring Boot
- **Frontend:** Thymeleaf, Bootstrap 5
- **Cloud:** AWS RDS Databases
- **Cơ Sở Dữ Liệu:** MySQL 8.0.40
- **ORM:** JPA (Java Persistence API)
- **Xác Thực:** JWT (JSON Web Tokens)

---

## Yêu Cầu Trước Khi Cài Đặt

1. **JDK** 17 hoặc cao hơn
2. **Maven** 3.8+
3. **MySQL** Server
4. **IDE** (IntelliJ IDEA, Eclipse, hoặc VS Code với hỗ trợ Java)
5. **Git** để quản lý phiên bản

---

## Hướng Dẫn Cài Đặt

1. **Clone repository:**
   ```bash
   git clone https://github.com/v2tuan/Spring_FashionWeb.git
   ```

2. **Di chuyển vào thư mục dự án:**
   ```bash
   cd Spring_FashionWeb
   ```

3. **Cấu hình cơ sở dữ liệu:**
   - Tạo một cơ sở dữ liệu MySQL tên `fashionista`.
   - Cập nhật tệp `/application.yaml` (hoặc /application.properties) trong `src/main/resources` với thông tin đăng nhập MySQL của bạn:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/fashionista
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Truy cập ứng dụng:**
   - Cổng khách hàng: `http://localhost:8091/home`
   - Cổng quản lý: 'http://localhost:8091/manager'
   - Cổng quản trị: `http://localhost:8091/admin`

---

## Quy Tắc Đóng Góp

1. Fork repository.
2. Tạo một nhánh mới cho tính năng hoặc sửa lỗi của bạn:
   ```bash
   git checkout -b feature-name
   ```
3. Commit thay đổi của bạn:
   ```bash
   git commit -m "Mô tả về tính năng/sửa lỗi"
   ```
4. Push nhánh của bạn:
   ```bash
   git push origin feature-name
   ```
5. Tạo pull request và mô tả thay đổi của bạn.

---

## Giấy Phép
Dự án này được cấp phép theo [MIT License](LICENSE).

---

## Thành Viên
  - Võ Nguyễn Hòa Lạc Dương - 22110304
  - Đỗ Văn Thường - 22110432
  - Võ Văn Tuấn - 22110450
  - Trần Triệu Vĩ - 2210459

