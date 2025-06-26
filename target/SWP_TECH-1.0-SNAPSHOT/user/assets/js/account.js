
// show / hide form login
const elementToHide = document.getElementById("register-active-show");
const showFormLogin = document.getElementById("login-active-show");
window.addEventListener('hashchange', function () {
    handelShowForm();
});

// Đảm bảo rằng khi trang được tải, phần tử "register-active-show" có trạng thái hiển thị ban đầu phù hợp
window.addEventListener('load', function () {
    handelShowForm();
});

const handelShowForm = () => {
    let hash = window.location.hash;
    if (hash === "#pills-register") {
        document.querySelector("title").innerHTML = "Register";
        document.getElementById("tab-login").classList.remove("active");
        document.getElementById("tab-register").classList.add("active");
        document.getElementById("pills-login").classList.remove("active", "show");
        
        // Hiển thị phần tử "register-active-show"
        elementToHide.style.display = "block"; // Hoặc "inline" hoặc giá trị hiển thị mong muốn
        showFormLogin.style.display = "none";
    } else {
        document.querySelector("title").innerHTML = "Login";
        document.getElementById("tab-login").classList.add("active");
        document.getElementById("tab-register").classList.remove("active");
        document.getElementById("pills-login").classList.add("active", "show");
        // Ẩn phần tử "register-active-show"
        elementToHide.style.display = "none";
        showFormLogin.style.display = "block";
    }
}

