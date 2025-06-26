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
        parentNode.classList.add("invalid");
        parentNode.querySelector('.message_error').style.display = 'block';
        parentNode.querySelector('.message_error').innerHTML = message;
    }
//  function handle message when input is valid
    function handleSuccess(element) {
        const parentNode = element.parentElement;
        parentNode.classList.remove("invalid");
        parentNode.querySelector('.message_error').style.display = 'none';
        parentNode.querySelector('.message_error').innerHTML = "";
    }
//  handle submit event check if all input pass. send data else not send

    submitBtn.addEventListener('click', function (e) {
        let isValid = true;
        inputsToValidate.forEach(function (item) {
            if (!checkInput(item.element, item.message, item.regex)) {
                isValid = false;
            }
        })
        if (!isValid) {
            e.preventDefault();
        }
    })
}

