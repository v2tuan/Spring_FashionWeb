function generateAvatar() {
    // Lấy token từ localStorage
    const token = localStorage.getItem("token");
    // Decode the JWT token using jwt-decode
    const decoded = jwt_decode(token);

    // Extract the username from the 'sub' claim (subject)
    const username = decoded.sub;
    var svg = document.getElementById('avatar');
    var svg_2 = document.getElementById('avatar_2');
    // Dùng jdenticon để tạo hình ảnh dựa trên tên người dùng
    jdenticon.update(svg, username);
    jdenticon.update(svg_2, username);
    console.log(username)
}

// Đảm bảo mã JavaScript chỉ chạy sau khi DOM tải xong
document.addEventListener('DOMContentLoaded', function () {
    generateAvatar();
});