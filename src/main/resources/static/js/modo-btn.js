
// show or hide sidebar
const menuBtn = document.querySelector('#menu-btn'); 
const closeBtn = document.querySelector('#close-btn'); 
const sidebar = document.querySelector('aside');
menuBtn.addEventListener('click', () => {
    sidebar.style.display = 'block';
})
closeBtn.addEventListener('click', () => {
    sidebar.style.display = 'none';
})


const themeBtn = document.querySelector('.theme-btn');
themeBtn.addEventListener('click', () => {
    document.body.classList.toggle('dark-theme');

    themeBtn.querySelector('span:first-child').classList.toggle('active');

    themeBtn.querySelector('span:last-child').classList.toggle('active');   

})

