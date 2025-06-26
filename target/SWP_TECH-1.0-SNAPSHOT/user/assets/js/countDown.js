let distance = 15 * 60 * 1000;
let countDown = setInterval(function() {
  distance -=1000;
  let days = Math.floor(distance / (1000 * 60 * 60 * 24));
  let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
  let seconds = Math.floor((distance % (1000 * 60)) / 1000);
    document.querySelector('.box-count-down-time').innerHTML = 
    `
    <div class="time-item">
        <span>${days}</span>
        <span>Day</span>
    </div>
    <div class="time-item">
        <span>${hours}</span>
        <span>Hours</span>
    </div>
    <div class="time-item">
        <span>${minutes}</span>
        <span>Min</span>
    </div>
    <div class="time-item">
        <span>${seconds}</span>
        <span>Sec</span>
    </div>
    `;
    if(distance == 0) {
        clearInterval(countDown);
    }
}, 1000);