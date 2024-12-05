$(document).ready(function (){
    $('#submit').click(function (){
        // Login Handler
        let email = document.getElementById('user_login_email').value;
        let password = document.getElementById('user_login_password').value;

        let basicInfo = JSON.stringify({
            email: email,
            password: password
        });

        $.ajax({
            type: "POST",
            url: "/auth/token",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: basicInfo,
            success: function (data) {
                localStorage.token = data.token;
                // Lưu JWT vào cookie
                document.cookie = `token=${data.token}; path=/; secure; SameSite=Strict`;
                // alert('Got a token from the server! Token: ' + data.token);
                alert('Đăng nhập thành công')
                window.location.href = "/home";
            },
            error: function () {
                alert("Failed to log in");
            }
        });

    });

    function logout() {
        console.log('Đang thực hiện đăng xuất...');
        localStorage.clear(); // Xóa localStorage
        window.location.href = "/login"; // Chuyển hướng đến trang login
    }
});