// validation form
const name = document.getElementById('name'),
    email = document.getElementById('email'),
    phone = document.getElementById('phone'),
    comment = document.getElementById('message'),
    submitBtn = document.querySelector('.btn.btn-primary');
const messageName = "Full name must be greater 2  characters",
    messageEmail = "Email is not valid. example: username@gmail.com",
    messagePhone = "Phone number is not valid",
    messageComment = "Message must be greater than 20 characters.";
// array to save all input to check, message show error of each and a string regex to check 
const inputsToValidate = [
    { element: name,message: messageName, regex: /^.{2,}$/},
    { element: phone, message: messagePhone, regex:  /^(0|\+84)[0-9]{9}$/},
    { element: email,message: messageEmail, regex: /^[^\s@]+@[^\s@]+\.[^\s@]{2,4}$/},
    { element: comment, message: messageComment, regex: /^[\s\S]{20,}$/}
];

// for each item in array input check when blur and when enter in input again clear show error
inputsToValidate.forEach(function(item) {
    item.element.addEventListener('blur', function() {
        checkInput(item.element, item.message, item.regex);
    })
    item.element.addEventListener('input', function() {
        const parentNode = item.element.parentElement;
        parentNode.querySelector('.message_error').style.display = 'none';
    })
})

// check error of element if no match with regex call function show error and return false
// else return true and call function show success
function checkInput(ele, message, regex) {
    let messageError = '';
    if(ele.value.trim() == "") {
        messageError = "This field is required";
    } else if(!ele.value.match(regex)) {
        messageError = message;
    } else {
        messageError = "";
    }

    if(messageError.trim().length != 0) {
        showErrorMessage(ele,messageError);
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
}
//  handle submit event check if all input pass. send data else not send
submitBtn.addEventListener('click', function(e) {
    let isValid = true;
    inputsToValidate.forEach(function(item) {
        if(!checkInput(item.element, item.message, item.regex)){
            isValid = false;
        }
    })
    if(!isValid) {
        e.preventDefault();
    }
})