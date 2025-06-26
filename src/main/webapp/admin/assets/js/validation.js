var isImage = true;
function validation(inputsToValidate, submitBtn) {
// for each item in array input check when blur and when enter in input again clear show error
    inputsToValidate.forEach(function (item) {
        item.element.addEventListener('blur', function () {
            checkInput(item.element, item.message, item.regex);
        })
        item.element.addEventListener('input', function () {
            const parentNode = item.element.parentElement;
            parentNode.querySelector('.message_error').innerHTML = "";
        })
    })

// check error of element if no match with regex call function show error and return false
// else return true and call function show success
    function checkInput(ele, message, regex) {
        let messageError = '';
        if (regex == "image") {
            if (isImage) {
                messageError = "";
            } else {
                messageError = message;
            }
        } else
        if (regex != false) {
            if (!ele.value.match(regex)) {
                messageError = message;
            } else {
                messageError = "";
            }
        } else {
            if (ele.value == "") {
                messageError = "This field is required";
            } else {
                messageError = "";
            }
        }
        if (messageError.trim().length != 0) {
            showErrorMessage(ele, messageError);
            return false;
        } else {
            handleSuccess(ele);
            return true;
        }
    }

//  function show message on each input when have a error
    function showErrorMessage(element, message) {
        const parentNode = element.parentElement;
        parentNode.querySelector('.message_error').style.display = 'block';
        parentNode.querySelector('.message_error').innerHTML = message;
    }
//  function handle message when input is valid
    function handleSuccess(element) {
        const parentNode = element.parentElement;
        parentNode.querySelector('.message_error').style.display = 'none';
        parentNode.querySelector('.message_error').innerHTML = "";
    }
//  handle submit event check if all input pass. send data else not send

    submitBtn.addEventListener('click', function (e) {
        const price = document.querySelector("#price"),
                priceSale = document.querySelector("#price-sale");
        let isValid = true;
        inputsToValidate.forEach(function (item) {
            if (!checkInput(item.element, item.message, item.regex)) {
                isValid = false;
            }
        })
        if (price != null && priceSale != null) {
            if (parseInt(price.value, 10) < parseInt(priceSale.value)) {
                showErrorMessage(priceSale, "Price sale must be less than price");
                isValid = false;
            }
        }
        const valueCode = document.querySelector("#value"),
                limit = document.querySelector("#limit");
        if (valueCode != null && limit != null) {
            if (parseInt(limit.value, 10) <= 0) {
                showErrorMessage(limit, "Limit of voucher must be greater 0");
                isValid = false;
            }
            if (parseInt(valueCode.value, 10) <= 0) {
                showErrorMessage(valueCode, "Value of voucher must be greater 0");
                isValid = false;
            }
        }
        if (!isValid) {
            e.preventDefault();
        }
    })
}

let objectURLs = [];
let isClearCache = false;
let allowedExtension = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/bmp'];
const previewImage = (event, elementPreview) => {
    let boxShowImgPreview = document.querySelector(elementPreview);
    if (!isClearCache) {
        isImage = true;
        objectURLs.forEach(url => URL.revokeObjectURL(url));
        objectURLs = [];
        isClearCache = true;
    }
    const lengthOfImage = event.target.files.length;
    let htmlRenderPreview = "";
    for (let i = 0; i < lengthOfImage; i++) {
        isClearCache = false;
        const srcImg = event.target.files[i];
        if (allowedExtension.indexOf(srcImg.type) <= -1) {
            isImage = false;
            return;
        }
        const src = URL.createObjectURL(srcImg);
        objectURLs.push(src);
        htmlRenderPreview += `<img class="show-img-preview" src="${src}" alt="image-preview"/>`;
    }
    boxShowImgPreview.innerHTML = htmlRenderPreview;
};
let objectURLColors = [];
let isClearCacheColor = false;
function previewImagesColor(event, containerSelector) {
    const container = document.querySelector(containerSelector);
    container.innerHTML = "";
    
    if (!isClearCacheColor) {
        isImage = true;
        objectURLColors.forEach(url => URL.revokeObjectURL(url));
        objectURLColors = [];
        isClearCacheColor = true;
    }
    
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
        const img = document.createElement("img");
        isClearCacheColor = false;
        const srcImg = event.target.files[i];
        if (allowedExtension.indexOf(srcImg.type) <= -1) {
            isImage = false;
            return;
        }
        
        const src = URL.createObjectURL(srcImg);
        objectURLColors.push(src);
        img.src = src;
        container.appendChild(img);
        
        const colorInput = document.createElement("input");
        colorInput.type = "text";
        colorInput.required = true;
        colorInput.name = "colorName" + (i + 1);
        colorInput.placeholder = "Color Name";
        container.appendChild(colorInput);
        
        const quantityInput = document.createElement("input");
        quantityInput.type = "number";
        quantityInput.required = true;
        quantityInput.name = "quantityColor" + (i + 1);
        quantityInput.placeholder = "Quantity";
        quantityInput.min = "1";
        container.appendChild(quantityInput);
    }
}


