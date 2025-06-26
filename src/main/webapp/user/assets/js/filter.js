//const filterProduct = (page) => {
//    const idCategories = document.querySelectorAll(".category-filter");
//    const from = document.querySelector("#input-from-price").value;
//    const to = document.querySelector("#input-to-price").value;
//    const filterTime = document.querySelector('.filter-time').value;
//    let ids = [];
//    for (let i = 0; i < idCategories.length; i++) {
//        if (idCategories[i].checked) {
//            ids.push(idCategories[i].value);
//        }
//    }
//    const data = {
//        idCategory: ids,
//        from: from,
//        to: to,
//        time: filterTime
//    };
//    $.ajax({
//        type: 'POST',
//        url: '/filter',
//        data: JSON.stringify(data),
//        contentType: 'application/json',
//        success: function (response) {
//            $(page).html(response);
//            let numberOfCart = document.querySelectorAll(".cart-filter");
//            let spanShow =`<span>Showing ${numberOfCart.length} relults</span>`;
//            $(".message-show-item").html(spanShow);
//        },
//        error: function (xhr, status, error) {
//            console.log('Lỗi: ' + error);
//        }
//    });
//    document.querySelector(".pagination").style.display = "none";
//    document.querySelector(".wrapper-product").style.marginBottom = "20px";
//    
//};
//
//
