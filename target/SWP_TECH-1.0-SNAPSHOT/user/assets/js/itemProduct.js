const changeImgPreview = (ele)=> {
    const mainImg = document.querySelector(".box-main-product img");
    mainImg.src = ele.querySelector("img").src;
    const imgActive = document.querySelector(".item-img-desc.active");
    imgActive.classList.remove("active");
    ele.classList.add("active");
}

const changeColorProduct = (ele)=> {
    const colorActive = document.querySelector(".item-color label.active");
    colorActive.classList.remove("active");
    ele.classList.add("active");
}

const changeContent = (type, ele)=> {
    let headerTitleDesc = document.querySelectorAll(".item-header-desc.active");
    let mainContentDesc = document.querySelectorAll(".main-desc-content.active");
    headerTitleDesc.forEach((item)=> {
        item.classList.remove("active");
    });
    mainContentDesc.forEach((item)=> {
        item.classList.remove("active");
    });
    ele.classList.add("active");
    document.querySelector(type).classList.add("active");
}

const updateCommentForm = (ele)=> {
    const comment = ele.parentElement.parentElement.querySelector(".comment-content");
    let commentContentUpdate = `<input required type="text" name="comment" class="update-comment-input" value="${comment.innerText}">
    <button class="btn btn-update-comment" name="btn-update-comment">Update</button>
    <span class="btn btn-update-comment cancel-update" onclick="closeCommentForm(this, '${comment.innerText}')">Cancel</span>`;
    comment.innerHTML = commentContentUpdate;
}

const closeCommentForm = (ele, value)=> {
    ele.parentElement.innerText = value;
}