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
        event.target.value = "";  // Clear input
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

    event.target.value = "";
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
    // Tạo một hàng mới cho size và số lượng
    let sizeRow = `
    <div class="size-row row">
        <div class="col-md-4">
            <input type="text" class="form-control" placeholder="Tên Size">
        </div>
        <div class="col-md-4">
            <input type="number" class="form-control" placeholder="Số Lượng">
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

function submitForm(status) {
    const form = document.getElementById("productForm");
    const formData = new FormData(form);

    // Add status to the form data
    formData.append("status", status);

    // Handle file uploads
    const files = document.getElementById("imageInput").files;
    Array.from(files).forEach((file, index) => {
        const ext = file.name.split('.').pop();
        formData.append(`file${index}`, new File([file], `id${index + 1}.${ext}`, { type: file.type }));
    });

    // Submit form using AJAX (example)
    fetch(form.action, {
        method: "POST",
        body: formData,
    })
        .then(response => response.json())
        .then(data => console.log("Success:", data))
        .catch(error => console.error("Error:", error));
}
