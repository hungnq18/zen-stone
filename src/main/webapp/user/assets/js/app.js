// menu in mobile
const menuBtnOpen = document.querySelector(".btn-menu-mobile"),
    menuBtnClose = document.querySelector(".btn-close-menu")
    overlay = document.querySelector(".overlay"),
    menu = document.querySelector(".navbar");

menuBtnOpen.addEventListener("click", (e)=> {
    menu.classList.add("active");
    overlay.classList.add("active");
});

overlay.addEventListener("click", (e)=> {
    menu.classList.remove("active");
    overlay.classList.remove("active");
})
menuBtnClose.addEventListener("click", (e)=> {
    menu.classList.remove("active");
    overlay.classList.remove("active");
});

// product
const wrapperFilter = document.querySelector(".wrapper-filter");

function toggleFilter(ele) {
    if(wrapperFilter.classList.contains("active")) {
        wrapperFilter.classList.remove("active");
        ele.setAttribute("class", "bx bx-chevron-down");
    } else {
        wrapperFilter.classList.add("active");
        ele.setAttribute("class", "bx bx-chevron-up");
    }
}

// change page product
const changePage = (ele, event)=> {
    ele.querySelector(".link-page").click();
}