const maxImages = 5;
let images = [];

$(document).ready(function () {
    $("#imageInput").change(handleFileSelect);
    $("#imageContainer").sortable({
        update: updateOrder
    }).disableSelection();
    console.log(typeof $.ui); // Kết quả nên là "object" nếu jQuery UI được tải thành công.
    console.log(typeof $().sortable); // Kết quả nên là "function" nếu `.sortable()` khả dụng.

});

function handleFileSelect(event) {
    const files = Array.from(event.target.files);

    if (images.length + files.length > maxImages) {
        $("#alertMessage").fadeIn().delay(2000).fadeOut();

        return;
    }

    files.slice(0, maxImages - images.length).forEach(file => {
        const reader = new FileReader();
        reader.onload = (e) => {
            images.push(e.target.result);
            renderImages();
        };
        reader.readAsDataURL(file);
    });
}

function renderImages() {
    $("#imageContainer").empty();
    images.forEach((src, index) => {
        $("#imageContainer").append(`
        <div class="image-item" data-index="${index}">
          <img src="${src}" alt="Image ${index + 1}">
          <button class="remove-btn" onclick="removeImage(${index})">&times;</button>
        </div>
      `);
    });
    $("#imageCount").text(images.length);
    if (images.length != 0) {
        document.getElementById("image-upload-default").style.setProperty("display", "none", "important");
    }
    else {
        document.getElementById("image-upload-default").style.setProperty("display", "block", "important");
    }
}

function removeImage(index) {
    images.splice(index, 1);
    renderImages();
}

function updateOrder() {
    const reorderedImages = [];
    $("#imageContainer .image-item").each(function () {
        const index = $(this).data("index");
        reorderedImages.push(images[index]);
    });
    images = reorderedImages;
    renderImages();
}

//   them size va so luong
function addSize() {
    // Lấy số lượng size-row hiện tại để làm index
    let index = $('.size-row').length;

    // Tạo một hàng mới với các name dựa trên index
    let sizeRow = `
    <div class="size-row row">
        <div class="col-md-4">
            <input type="text" class="form-control" name="sizes[${index - 1}].name" placeholder="Tên Size">
        </div>
        <div class="col-md-4">
            <input type="number" class="form-control" name="sizes[${index - 1}].quantity" placeholder="Số Lượng">
        </div>
        <div class="col-md-2 d-flex align-items-center">
            <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeSize(this)">Xóa</button>
        </div>
    </div>
    `;

    // Thêm hàng mới vào container
    $('#sizesContainer').append(sizeRow);
}


function removeSize(button) {
    // Xóa hàng size
    $(button).closest('.row').remove();
}


document.addEventListener('DOMContentLoaded', function () {
    const mimeType = "image/jpeg"; // MIME type của ảnh (image/png, image/jpeg, v.v.)

    function base64ToBlob(base64, mimeType) {
        const byteCharacters = atob(base64.split(",")[1]);
        const byteNumbers = Array.from(byteCharacters, char => char.charCodeAt(0));
        const byteArray = new Uint8Array(byteNumbers);
        return new Blob([byteArray], { type: mimeType });
    }

    $('#productForm').on('submit', function (e) {
        // Hiển thị spinner khi bắt đầu xử lý
        const spinner = new bootstrap.Modal(document.getElementById('loadingSpinner'));
        spinner.show();

        e.preventDefault(); // Ngăn form submit mặc định

        var formData = new FormData(this); // Lấy dữ liệu từ form
// Append images to FormData
        images.forEach(function (image, index) {
            formData.append('images[' + index + ']', image); // Append each image as an array
        });

        images.forEach((base64, index) => {
            const file = new File([base64ToBlob(base64, mimeType)], `file${index + 1}.jpg`, { type: mimeType });
            formData.append("files", file); // Append từng file vào FormData
        });

        // Lấy token từ localStorage
        const token = localStorage.getItem("token");
        if (!token) {
            console.error('Token không tồn tại trong localStorage!');
            return;
        }

        // Decode the JWT token using jwt-decode
        const decoded = jwt_decode(token);
        const id = decoded.accId;

        // Gửi request bằng AJAX
        $.ajax({
            url: `/manager/create-product`, // URL server xử lý
            type: 'POST',          // Loại request
            // dataType: "json",
            processData: false,  // Không xử lý dữ liệu
            contentType: false,  // Không thiết lập content type
            data: formData,       // Dữ liệu từ form
            headers: {
                'Authorization': `Bearer ${token}` // Đính kèm JWT vào header
            },
            success: function (response) {
                spinner.hide(); // Ẩn spinner sau khi xử lý thành công
                alert('Response: ' + response)
                console.log('Response:', response); // Xử lý thành công
            },
            error: function (xhr, status, error) {
                spinner.hide(); // Ẩn spinner sau khi xử lý thành công
                // Nếu có lỗi, thông báo cho người dùng
                alert('Lỗi: ' + xhr.responseJSON.body);
            }
        });
    });
});