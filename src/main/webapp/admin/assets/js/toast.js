function toast({
    title = '',
    message = '',
    type = 'success',
    duration = 3000
}) {
    const main = document.getElementById('toast');
    if(main) {
        const toast = document.createElement('div');
        const delay = (duration / 1000).toFixed(2);
        toast.style.animation =`slideToastMessage 0.2s ease, fadeOut linear 1s ${delay}s forwards`;
        const IDtoastDuration = setTimeout(function () {
            main.removeChild(toast);
        }, duration + 1000);
    
        toast.onclick = function(e) {
            if(e.target.closest('.toast__close')) {
               main.removeChild(toast);
               clearTimeout(IDtoastDuration);
            }
        }
        const icons = {
            success: 'bx bx-check',
            error: 'bx bxs-error-alt'
        }
        toast.classList.add('toast');
        toast.classList.add('toast', `toast__${type}`);
        toast.innerHTML = `
            <div class="toast__icon">
            <i class="${icons[type]}"></i>
            </div>
            <div class="toast__body">
                <h3 class="toast__body--title">${title}</h3>
                <p class="toast__body--conttent">
                ${message}
                </p>
            </div>
            <div class="toast__close">
            <i class="bx bx-x"></i>
            </div>
        `
        main.appendChild(toast);
    }
}

function showSuccess(message) {
    console.log("ok");
    toast({
        title: 'Success',
        message: message,
        type: 'success', 
        duration: 4000
    })
}
function showError(message) {
    toast({
        title: 'Error',
        message: message,
        type: 'error', 
        duration: 5000
    })
}