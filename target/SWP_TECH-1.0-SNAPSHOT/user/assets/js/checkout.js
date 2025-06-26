const filterAddress = (type, id) => {
    let data = [];
    if (type == '#district') {
        data = {
            province: id,
            district: ""
        };
        $("#ward").html("<option value=''>Choose  a ward</option>");
    } else if (type == '#ward') {
        data = {
            province: "",
            district: id
        };
    }
    $.ajax({
        type: 'POST',
        url: '/address-checkout',
        data: JSON.stringify(data),
        contentType: 'json',
        success: function (response) {
            $(type).html(response);
        },
        error: function (xhr, status, error) {
            console.log('Lỗi: ' + error);
        }
    });
};

const messageVoucher = document.querySelector(".message-voucher");
const applyVoucher = (code) => {
    let voucherInput = document.querySelector(".voucher-apply-code");
    let billPrice = document.querySelector(".price-bill").value;
    let userID = document.querySelector(".userId-user").value;
    let showTotalAterVoucher = document.querySelector(".apply-voucher");
    $.ajax({
        type: 'POST',
        url: '/voucher',
        data: JSON.stringify(
                {
                    code: code,
                    billPrice: billPrice,
                    userId: userID
                }
        ),
        contentType: 'application/json',
        success: function (response) {
            if (response == "inactive") {
                messageVoucher.innerHTML = "Voucher can not apply";
                messageVoucher.style.color = "red";
                showTotalAterVoucher.innerHTML = "";
                voucherInput.value = "";
            } else if (response == "used") {
                messageVoucher.style.color = "red";
                messageVoucher.innerHTML = "You have used this voucher";
                showTotalAterVoucher.innerHTML = "";
                voucherInput.value = "";
            }else {
                let totalAfterVoucher = billPrice - response;
                messageVoucher.innerHTML = "Voucher is apply -" + response;
                let applyVoucherShow = `<p class="ordered-item-title">
                                                Total after discount
                                            </p>
                                            <p class="ordered-item-title black-bold">${currencyFormat(totalAfterVoucher, "VND")}</p>`;
                showTotalAterVoucher.innerHTML = applyVoucherShow;
            }
        },
        error: function (xhr, status, error) {
            console.log('Lỗi: ' + error);
        }
    });
};


function currencyFormat(number, suffix) {
    if (number !== 0) {
        const decimalFormat = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD' // Change the currency code as needed
        });
        const formattedNumber = decimalFormat.format(number);

        // Remove the dollar sign from the beginning
        const trimmedNumber = formattedNumber.replace(/^\$/, '');

        // Remove two trailing zeros
        const finalFormattedNumber = trimmedNumber.replace(/\.00$/, '');

        return finalFormattedNumber + ' ' + suffix;
    }
    return '0 ' + suffix;
}