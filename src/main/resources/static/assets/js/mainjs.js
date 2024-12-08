$(document).ready(function () {
    $('#submit').click(function () {
        // Login Handler
        let email = document.getElementById('user_login_email').value;
        let password = document.getElementById('user_login_password').value;

        let basicInfo = JSON.stringify({
            email: email,
            password: password
        });
        $.ajax({
            type: "POST",
            url: "/api/login",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: basicInfo,
            success: function (data) {
                localStorage.token = data.token;
                // alert('Got a token from the server! Token: ' + data.token);
                alert('Đăng nhập thành công')
                window.location.href = "/home";
            },
            error: function(xhr) {
                // Đảm bảo lấy đúng dữ liệu lỗi từ backend
                var errorMessages = xhr.responseJSON ? xhr.responseJSON.body : [];

// Xóa các lỗi cũ
                $('#emailError').text('');
                $('#passwordError').text('');

// Kiểm tra nếu body là một mảng và có lỗi nào không
                if (Array.isArray(errorMessages) && errorMessages.length > 0) {
                    // Hiển thị lỗi mới
                    errorMessages.forEach(function (error) {
                        var field = error.field;
                        var message = error.message;

                        // Hiển thị lỗi tại trường tương ứng
                        if (field === 'email') {
                            $('#emailError').text(message);
                        } else if (field === 'password') {
                            $('#passwordError').text(message);
                        }
                    });
                } else {
                    // Nếu danh sách lỗi rỗng hoặc không có lỗi cụ thể, hiển thị thông báo lỗi chung
                    alert("Sai email hoặc mật khẩu");
                }
            }
            // error: function () {
            //     alert("Failed to log in");
            // }
        });

    });

    // Nhan nut dang ki
    document.getElementById('registerButton').addEventListener('click', function () {
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        // Dữ liệu cần gửi
        const data = {
            email: email,  // Thay thế bằng thông tin cần gửi
            password: password
        };

        // Hiển thị spinner khi bắt đầu xử lý
        const spinner = new bootstrap.Modal(document.getElementById('loadingSpinner'));
        spinner.show();

        // Sử dụng $.ajax() để gửi yêu cầu
        $.ajax({
            url: '/api/register',  // Địa chỉ controller của bạn
            type: 'POST',  // Phương thức HTTP (POST)
            contentType: 'application/json',  // Định dạng dữ liệu là JSON
            data: JSON.stringify(data),  // Chuyển đổi dữ liệu thành chuỗi JSON
            success: function (response) {
                spinner.hide(); // Ẩn spinner sau khi xử lý thành công
                // Nếu thành công, mở modal và bắt đầu đếm ngược
                const modal = new bootstrap.Modal(document.getElementById('verificationModal'));
                modal.show();
                startCountdown(response.verificationCodeExpiresAt);
            },
            error: function (xhr, status, error) {
                spinner.hide(); // Ẩn spinner sau khi xử lý thành công
                // Nếu có lỗi, thông báo cho người dùng
                alert(xhr.responseJSON.body);
            }
        });
    });


});