$(document).ready(function () {
    // Kiểm tra trạng thái đăng nhập từ localStorage
    function checkLoginStatus() {
        const token = localStorage.getItem("token"); // Giả sử bạn lưu token trong localStorage
        if (token) {
            // Giải mã token bằng jwt-decode
            const decoded = jwt_decode(token);

            // Lấy thời gian hết hạn từ claim 'exp' (expiration)
            const expirationTime = decoded.exp * 1000;  // Chuyển từ giây sang mili giây

            // Lấy thời gian hiện tại (mili giây)
            const currentTime = Date.now();

            // Kiểm tra xem token đã hết hạn chưa
            if (currentTime > expirationTime) {
                // Nếu không có token, người dùng chưa đăng nhập
                $("#button-profile").css("display", "none").attr("style", "display: none !important"); // Ẩn nút profile với !important
                $("#button-login").css("display", "block").attr("style", "display: block !important"); // Hiển thị nút login với !important
            } else {
                // Nếu có token, người dùng đã đăng nhập
                $("#button-login").css("display", "none").attr("style", "display: none !important"); // Ẩn nút login với !important
                $("#button-profile").css("display", "block").attr("style", "display: block !important"); // Hiển thị nút profile với !important
            }
        } else {
            // Nếu không có token, người dùng chưa đăng nhập
            $("#button-profile").css("display", "none").attr("style", "display: none !important"); // Ẩn nút profile với !important
            $("#button-login").css("display", "block").attr("style", "display: block !important"); // Hiển thị nút login với !important
        }
    }

    checkLoginStatus(); // Gọi hàm kiểm tra trạng thái đăng nhập khi trang được tải

// // Reset timer when modal is shown
// const modal = document.getElementById('verificationModal');
// modal.addEventListener('shown.bs.modal', () => {
//     startCountdown();
// });

// Auto-move between input fields
    const codeInputs = document.querySelectorAll('.code-input');
    codeInputs.forEach((input, index) => {
        input.addEventListener('input', (e) => {
            if (e.target.value.length === 1) {
                const nextInput = codeInputs[index + 1];
                if (nextInput) {
                    nextInput.focus();
                }
            } else if (e.target.value.length === 0) {
                const prevInput = codeInputs[index - 1];
                if (prevInput) {
                    prevInput.focus();
                }
            }
        });

        // Handle backspace to move to the previous input
        input.addEventListener('keydown', (e) => {
            if (e.key === 'Backspace' && input.value === '') {
                const prevInput = codeInputs[index - 1];
                if (prevInput) {
                    prevInput.focus();
                }
            }
        });
    });

});

const startCountdown = (expiryDate) => {
    let timeLeft = 60;
    // Chuyển expiryDate (ISO-8601) thành timestamp
    const expiryTime = new Date(expiryDate).getTime();

    // Lấy phần tử hiển thị đếm ngược
    const timerElement = document.getElementById("timer");

    const countdown = setInterval(() => {
        const now = new Date().getTime(); // Lấy thời gian hiện tại
        const timeLeft = expiryTime - now; // Tính thời gian còn lại

        // Nếu hết thời gian, dừng bộ đếm
        if (timeLeft <= 0) {
            clearInterval(countdown);
            timerElement.textContent = "Hết giờ!";
            return;
        }

        // Tính ngày, giờ, phút, giây còn lại
        const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
        const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

        // Hiển thị thời gian còn lại
        timerElement.textContent = `${days} ngày, ${hours} giờ, ${minutes} phút, ${seconds} giây`;
    }, 1000);
};

function verificationCode(){
    const email = document.getElementById("email").value;
    const codeInputs = $(".code-input");

    const code = codeInputs
        .map(function () {
            return $(this).val().trim();
        })
        .get()
        .join("");

    if (code.length !== 6 || !/^\d{6}$/.test(code)) {
        alert("Mã xác thực không hợp lệ. Vui lòng kiểm tra lại!");
        return;
    }

    // Dữ liệu cần gửi
    const data = {
        email: email,  // Thay thế bằng thông tin cần gửi
        verificationCode: code
    };

    // Hiển thị spinner khi bắt đầu xử lý
    const spinner = new bootstrap.Modal(document.getElementById('loadingSpinner'));
    spinner.show();

    // Sử dụng $.ajax() để gửi yêu cầu
    $.ajax({
        url: '/api/verify',  // Địa chỉ controller của bạn
        type: 'POST',  // Phương thức HTTP (POST)
        contentType: 'application/json',  // Định dạng dữ liệu là JSON
        data: JSON.stringify(data),  // Chuyển đổi dữ liệu thành chuỗi JSON
        success: function(response) {
            // Additional logic on success (e.g., close modal)
            const modal = bootstrap.Modal.getInstance(
                document.getElementById("verificationModal")
            );
            modal.hide();
            spinner.hide(); // Ẩn spinner sau khi xử lý thành công
            alert(response);
            window.location.href = "/login";
        },
        error: function(xhr, status, error) {
            spinner.hide(); // Ẩn spinner sau khi xử lý thành công
            // Nếu có lỗi, thông báo cho người dùng
            alert(xhr.responseJSON.body);
        }
    });
}
// Nhan nut xac nhan code
document.getElementById('btn-verificationCode').addEventListener('click', function () {

    // // Additional logic on success (e.g., close modal)
    // const modal = bootstrap.Modal.getInstance(
    //     document.getElementById("verificationModal")
    // );
    // modal.hide();

    const email = document.getElementById("email").value;
    const codeInputs = $(".code-input");

    const code = codeInputs
        .map(function () {
            return $(this).val().trim();
        })
        .get()
        .join("");

    if (code.length !== 6 || !/^\d{6}$/.test(code)) {
        alert("Mã xác thực không hợp lệ. Vui lòng kiểm tra lại!");
        return;
    }

    // Dữ liệu cần gửi
    const data = {
        email: email,  // Thay thế bằng thông tin cần gửi
        verificationCode: code
    };

    // Hiển thị spinner khi bắt đầu xử lý
    const spinner = new bootstrap.Modal(document.getElementById('loadingSpinner'));
    spinner.show();

    // Sử dụng $.ajax() để gửi yêu cầu
    $.ajax({
        url: '/api/verify',  // Địa chỉ controller của bạn
        type: 'POST',  // Phương thức HTTP (POST)
        contentType: 'application/json',  // Định dạng dữ liệu là JSON
        data: JSON.stringify(data),  // Chuyển đổi dữ liệu thành chuỗi JSON
        success: function(response) {
            // Additional logic on success (e.g., close modal)
            const modal = bootstrap.Modal.getInstance(
                document.getElementById("verificationModal")
            );
            modal.hide();
            spinner.hide(); // Ẩn spinner sau khi xử lý thành công
            alert(response);
            window.location.href = "/login";
        },
        error: function(xhr, status, error) {
            spinner.hide(); // Ẩn spinner sau khi xử lý thành công
            // Nếu có lỗi, thông báo cho người dùng
            alert(xhr.responseJSON.body);
        }
    });
});

function register(){
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
        url: '/account/signup',  // Địa chỉ controller của bạn
        type: 'POST',  // Phương thức HTTP (POST)
        contentType: 'application/json',  // Định dạng dữ liệu là JSON
        data: JSON.stringify(data),  // Chuyển đổi dữ liệu thành chuỗi JSON
        success: function(response) {
            spinner.hide(); // Ẩn spinner sau khi xử lý thành công
            // Nếu thành công, mở modal và bắt đầu đếm ngược
            const modal = new bootstrap.Modal(document.getElementById('verificationModal'));
            modal.show();
            startCountdown(response.verificationCodeExpiresAt);
        },
        error: function(xhr, status, error) {
            spinner.hide(); // Ẩn spinner sau khi xử lý thành công
            // Nếu có lỗi, thông báo cho người dùng
            alert(xhr.responseJSON.body);
        }
    });
}