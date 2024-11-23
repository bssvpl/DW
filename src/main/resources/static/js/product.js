document.addEventListener('DOMContentLoaded', function() {
    const thumbnails = document.querySelectorAll('.thumbnail');
    const mainImage = document.getElementById('mainImage');

    if (thumbnails.length > 0) {
        mainImage.src = thumbnails[0].src;
        thumbnails[0].classList.add('active');
    }

    thumbnails.forEach(thumb => {
        thumb.addEventListener('click', () => {
            thumbnails.forEach(t => t.classList.remove('active'));
            thumb.classList.add('active');
            mainImage.style.opacity = '0';
            setTimeout(() => {
                mainImage.src = thumb.src;
                mainImage.style.opacity = '1';
            }, 150);
        });
    });

    const minusBtn = document.querySelector('.minus');
    const plusBtn = document.querySelector('.plus');
    const quantityInput = document.querySelector('.quantity-input');

    minusBtn.addEventListener('click', () => {
        const currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    });

    plusBtn.addEventListener('click', () => {
        const currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
    });

    const sizeButtons = document.querySelectorAll('.size-btn');
    sizeButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            sizeButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
        });
    });

    const shippingButtons = document.querySelectorAll('.shipping-btn');
    shippingButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            shippingButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
        });
    });

    const cartBtn = document.querySelector('.cart-preview-box');
    const cartModal = document.querySelector('.cart-modal');
    const cartOverlay = document.querySelector('.cart-overlay');
    const closeCart = document.querySelector('.close-cart');
    const cartItems = document.querySelector('.cart-items');
    const totalAmount = document.querySelector('.total-amount');

    // Funciones para manejar el localStorage
    function saveToLocalStorage(product) {
        let cart = getCartFromLocalStorage();
        // Verificar si el producto ya existe en el carrito (incluyendo talla)
        const existingProductIndex = cart.findIndex(item => 
            item.title === product.title && 
            item.price === product.price &&
            item.size === product.size // Agregar talla a la comparación
        );

        if (existingProductIndex !== -1) {
            // Si existe, actualizar cantidad
            cart[existingProductIndex].quantity += product.quantity;
        } else {
            // Si no existe, agregar nuevo producto con todos los datos
            cart.push({
                title: product.title,
                price: product.price,
                quantity: product.quantity,
                image: product.image,
                size: product.size,
                shipping: product.shipping,
                storeName: product.storeName
            });
        }
        
        localStorage.setItem('cart', JSON.stringify(cart));
    }

    function getCartFromLocalStorage() {
        return JSON.parse(localStorage.getItem('cart')) || [];
    }

    function clearCart() {
        localStorage.removeItem('cart');
    }

    // Modificar la función updateCart
    function updateCart(product = null) {
        cartItems.innerHTML = '';
        
        if (!product) {
            const savedCart = getCartFromLocalStorage();
            if (savedCart.length === 0) {
                // Mostrar mensaje de carrito vacío
                const emptyCart = document.createElement('div');
                emptyCart.className = 'empty-cart';
                emptyCart.innerHTML = `
                    <div class="empty-cart-content">
                        <i class="fas fa-shopping-cart empty-cart-icon"></i>
                        <p>Tu carrito está vacío</p>
                        <span>¡Agrega algunos productos!</span>
                    </div>
                `;
                cartItems.appendChild(emptyCart);
                return;
            }

            // Mostrar productos guardados
            let total = 0;
            savedCart.forEach(item => {
                createCartItem(item);
                total += item.price * item.quantity;
            });
            
            // Agregar el footer con el total y el botón
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
            return;
        }

        // Si hay un nuevo producto, guardarlo y actualizar vista
        saveToLocalStorage(product);
        const cart = getCartFromLocalStorage();
        cart.forEach(item => {
            createCartItem(item);
        });
        
        // Calcular y mostrar el total
        const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
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

    // Función auxiliar para crear elementos del carrito
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
                        ${product.shipping}
                    </div>
                </div>
                <div class="cart-item-footer">
                    <div class="cart-item-price">S/${(product.price * product.quantity).toFixed(2)}</div>
                </div>
            </div>
        `;

        // Modificar el event listener para eliminar item
        cartItem.querySelector('.remove-item').addEventListener('click', function() {
            removeFromCart(this.dataset.title, this.dataset.size);
        });

        cartItems.appendChild(cartItem);
    }

    // Agregar función para eliminar items del carrito
    function removeFromCart(productTitle, productSize) {
        let cart = getCartFromLocalStorage();
        cart = cart.filter(item => item.title !== productTitle || item.size !== productSize);
        localStorage.setItem('cart', JSON.stringify(cart));
        updateCart(); // Actualizar vista del carrito
    }

    // Event listeners para el modal
    cartBtn.addEventListener('click', () => {
        updateCart(); // Esto mostrará los productos guardados en localStorage
        cartModal.classList.add('active');
        cartOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
    });

    function closeModal() {
        cartModal.classList.remove('active');
        cartOverlay.classList.remove('active');
        document.body.style.overflow = 'auto';
    }

    closeCart.addEventListener('click', closeModal);
    cartOverlay.addEventListener('click', closeModal);

    // Modificar el event listener del botón agregar al carrito
    const addToCartBtn = document.querySelector('.add-to-cart');
    addToCartBtn.addEventListener('click', (e) => {
        e.preventDefault();
        if (validateSelections()) {
            const selectedSize = document.querySelector('.size-btn.active').textContent;
            const selectedShipping = document.querySelector('.shipping-btn.active').querySelector('.title-container span').textContent;
            const firstThumbnail = document.querySelector('.thumbnail-gallery img').src;
            const storeName = document.querySelector('.store-name').textContent;

            const product = {
                title: document.querySelector('h2').textContent,
                size: selectedSize,
                shipping: selectedShipping,
                quantity: parseInt(document.querySelector('.quantity-input').value),
                storeName: storeName,
                image: firstThumbnail,
                price: parseFloat(document.querySelector('.price').textContent.replace('S/', ''))
            };
            
            updateCart(product);
            document.querySelector('.cart-dot').classList.add('active');
            
            // Mostrar notificación de éxito
            Swal.fire({
                title: '¡Producto agregado!',
                text: 'El producto se agregó al carrito correctamente',
                icon: 'success',
                timer: 1500,
                showConfirmButton: false
            });
        } else {
            // Mostrar error si no se seleccionaron todas las opciones
            Swal.fire({
                title: 'Error',
                text: 'Por favor selecciona talla y método de envío',
                icon: 'error',
                confirmButtonText: 'Ok'
            });
        }
    });

    // Función para validar selecciones
    function validateSelections() {
        const selectedSize = document.querySelector('.size-btn.active');
        const quantity = parseInt(document.querySelector('.quantity-input').value);
        const selectedShipping = document.querySelector('.shipping-btn.active');
        
        return selectedSize && quantity > 0 && selectedShipping;
    }

    // Modificar los event listeners de los botones de compra
    const buyNowBtn = document.querySelector('.buy-now');

    buyNowBtn.addEventListener('click', (e) => {
        e.preventDefault();
        if (validateSelections()) {
            // Aquí iría la lógica para compra directa
            console.log('Procediendo con la compra');
        }
    });

    // Deshabilitar visualmente los botones hasta que se hagan las selecciones
    function updateButtonsState() {
        const selectedSize = document.querySelector('.size-btn.active');
        const quantity = parseInt(document.querySelector('.quantity-input').value);
        const selectedShipping = document.querySelector('.shipping-btn.active');
        
        const isValid = selectedSize && quantity > 0 && selectedShipping;
        
        addToCartBtn.classList.toggle('disabled', !isValid);
        buyNowBtn.classList.toggle('disabled', !isValid);
    }

    // Agregar listeners para actualizar estado de botones
    document.querySelectorAll('.size-btn').forEach(btn => {
        btn.addEventListener('click', updateButtonsState);
    });
    
    document.querySelector('.quantity-input').addEventListener('change', updateButtonsState);
    
    document.querySelectorAll('.shipping-btn').forEach(btn => {
        btn.addEventListener('click', updateButtonsState);
    });

    // Llamar inicialmente para establecer el estado inicial
    updateButtonsState();
}); 