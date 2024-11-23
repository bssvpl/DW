document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.main-image');
    const nextBtn = document.querySelector('.next');
    let currentImageIndex = 0;
    let slideInterval;

    // Contenido para cada slide
    const slideContents = [
        {
            title: "GALLERY STYLE",
            description: "Lorem ipsum dolor sit amet consectetur. Felis integer nisl id potenti cursus.",
            buttonText: "Explorar ahora",
            buttonLink: "#"
        },
        {
            title: "NUEVA COLECCIÓN",
            description: "Descubre las últimas tendencias en moda y estilo para esta temporada.",
            buttonText: "Explorar ahora",
            buttonLink: "#"
        },
        {
            title: "OFERTAS ESPECIALES",
            description: "Aprovecha nuestros descuentos exclusivos en productos seleccionados.",
            buttonText: "Explorar ahora",
            buttonLink: "#"
        }
    ];

    // Función para actualizar el contenido
    function updateContent(index) {
        const content = slideContents[index];
        document.querySelector('.slider-title').textContent = content.title;
        document.querySelector('.slider-description').textContent = content.description;
        const button = document.querySelector('.slider-button');
        button.textContent = content.buttonText;
        button.href = content.buttonLink;
    }

    // Función para mostrar una imagen específica
    function showImage(index) {
        images.forEach(img => img.classList.remove('active'));
        images[index].classList.add('active');
        updateContent(index);
    }

    // Mostrar la primera imagen
    showImage(currentImageIndex);

    // Función para ir a la siguiente imagen
    function nextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        showImage(currentImageIndex);
    }

    // Iniciar el slider automático
    function startSlideShow() {
        slideInterval = setInterval(nextImage, 10000);
    }

    // Detener el slider automático
    function stopSlideShow() {
        clearInterval(slideInterval);
    }

    // Event listener solo para el botón next
    nextBtn.addEventListener('click', () => {
        stopSlideShow();
        nextImage();
        startSlideShow();
    });

    // Iniciar el slideshow automático
    startSlideShow();

    // Agregar la funcionalidad del carrito
    const cartBtn = document.querySelector('.carrito');
    const cartModal = document.querySelector('.cart-modal');
    const cartOverlay = document.querySelector('.cart-overlay');
    const closeCart = document.querySelector('.close-cart');
    const cartItems = document.querySelector('.cart-items');
    const cartDot = document.querySelector('.cart-dot');
    const addButtons = document.querySelectorAll('.btn_agregar');

    // Función para abrir el modal del carrito
    function openModal() {
        cartModal.classList.add('active');
        cartOverlay.classList.add('active');
        updateCart();
    }

    // Función para cerrar el modal del carrito
    function closeModal() {
        cartModal.classList.remove('active');
        cartOverlay.classList.remove('active');
    }

    // Función para crear elementos del carrito
    function createCartItem(product) {
        const cartItem = document.createElement('div');
        cartItem.className = 'cart-item';
        cartItem.innerHTML = `
            <div class="cart-item-left">
                <img src="${product.image}" class="cart-item-img" alt="${product.title}">
            </div>
            <div class="cart-item-details">
                <div class="cart-item-header">
                    <div class="cart-item-name">${product.title}</div>
                    <button class="remove-item" data-title="${product.title}" data-size="${product.size}">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
                <div class="cart-item-info">
                    <div class="cart-item-store">
                        <i class="fas fa-store"></i>
                        ${product.storeName}
                    </div>
                    <div class="cart-item-specs">
                        <span class="cart-item-size">
                            <i class="fas fa-tshirt"></i>
                            Talla: ${product.size}
                        </span>
                        <span class="cart-item-quantity">
                            <i class="fas fa-box"></i>
                            Cantidad: ${product.quantity}
                        </span>
                    </div>
                    <div class="cart-item-shipping">
                        <i class="fas fa-truck"></i>
                        Envío gratis
                    </div>
                </div>
                <div class="cart-item-footer">
                    <div class="cart-item-price">S/${(product.price * product.quantity).toFixed(2)}</div>
                </div>
            </div>
        `;
        return cartItem;
    }

    // Función para actualizar el carrito
    function updateCart() {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        cartItems.innerHTML = '';
        
        if (cart.length === 0) {
            cartItems.innerHTML = `
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart empty-cart-icon"></i>
                    <p>Tu carrito está vacío</p>
                    <span>¡Agrega algunos productos!</span>
                </div>
            `;
        } else {
            let total = 0;
            cart.forEach(product => {
                cartItems.appendChild(createCartItem(product));
                total += product.price * product.quantity;
            });

            const cartFooter = document.createElement('div');
            cartFooter.className = 'cart-footer';
            cartFooter.innerHTML = `
                <div class="cart-total">
                    <span>Total:</span>
                    <span class="total-amount">S/${total.toFixed(2)}</span>
                </div>
                <button class="go-to-cart-btn">
                    <span>Ir al carrito</span>
                    <i class="fas fa-arrow-right"></i>
                </button>
            `;
            cartItems.appendChild(cartFooter);
        }

        cartDot.classList.toggle('active', cart.length > 0);
    }

    // Función para agregar al carrito
    function addToCart(button) {
        const product = {
            title: button.dataset.title,
            price: parseFloat(button.dataset.price),
            image: button.dataset.image,
            storeName: button.dataset.store,
            size: 'M', // Puedes hacer esto dinámico con un selector de tallas
            quantity: 1,
            shipping: 'Envío gratis'
        };

        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        const existingProduct = cart.find(item => item.title === product.title && item.size === product.size);

        if (existingProduct) {
            existingProduct.quantity += 1;
        } else {
            cart.push(product);
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        updateCart();
    }

    // Función para remover del carrito
    function removeFromCart(title, size) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        cart = cart.filter(item => !(item.title === title && item.size === size));
        localStorage.setItem('cart', JSON.stringify(cart));
        updateCart();
    }

    // Event Listeners
    cartBtn.addEventListener('click', openModal);
    closeCart.addEventListener('click', closeModal);
    cartOverlay.addEventListener('click', closeModal);

    addButtons.forEach(button => {
        button.addEventListener('click', () => addToCart(button));
    });

    cartItems.addEventListener('click', function(e) {
        if (e.target.closest('.remove-item')) {
            const btn = e.target.closest('.remove-item');
            removeFromCart(btn.dataset.title, btn.dataset.size);
        }
    });

    // Actualizar carrito al cargar la página
    updateCart();
});
