document.addEventListener('DOMContentLoaded', function() {
    // Cargar los items del carrito
    function loadCartItems() {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        const cartItemsContainer = document.querySelector('.cart-items');
        const subtotalAmount = document.querySelector('.subtotal-amount');
        const shippingAmount = document.querySelector('.shipping-amount');
        const totalAmount = document.querySelector('.total-amount');
        const btnAmount = document.querySelector('.btn-amount');
        
        if (cart.length === 0) {
            cartItemsContainer.innerHTML = '<p class="empty-cart-message">No hay productos en el carrito</p>';
            shippingAmount.textContent = 'S/9.00';
            subtotalAmount.textContent = 'S/0.00';
            totalAmount.textContent = 'S/9.00';
            btnAmount.textContent = '9.00';
            return;
        }

        let subtotal = 0;
        cartItemsContainer.innerHTML = cart.map(item => {
            subtotal += item.price * item.quantity;
            return `
                <div class="cart-item">
                    <img src="${item.image}" alt="${item.title}" class="cart-item-image">
                    <div class="cart-item-details">
                        <div class="cart-item-info">
                            <div class="cart-item-name">${item.title}</div>
                            <div class="cart-item-price">S/${(item.price * item.quantity).toFixed(2)}</div>
                        </div>
                        <div class="cart-item-meta">
                            <span class="cart-item-quantity">Cantidad: ${item.quantity}</span>
                        </div>
                    </div>
                </div>
            `;
        }).join('');

        const shipping = 9;
        const total = subtotal + shipping;

        subtotalAmount.textContent = `S/${subtotal.toFixed(2)}`;
        shippingAmount.textContent = `S/${shipping.toFixed(2)}`;
        totalAmount.textContent = `S/${total.toFixed(2)}`;
        btnAmount.textContent = total.toFixed(2);
    }

    // Validación de tarjeta
    const cardInputs = {
        number: document.querySelector('.card-number'),
        expiry: document.querySelector('.card-expiry'),
        cvv: document.querySelector('.card-cvv')
    };

    // Formateo de número de tarjeta
    cardInputs.number.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(\d{4})/g, '$1 ').trim();
        e.target.value = value.substring(0, 19);
    });

    // Formateo de fecha de expiración
    cardInputs.expiry.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length >= 2) {
            value = value.substring(0, 2) + '/' + value.substring(2);
        }
        e.target.value = value.substring(0, 5);
    });

    // Limitar CVV a 3-4 dígitos
    cardInputs.cvv.addEventListener('input', function(e) {
        e.target.value = e.target.value.replace(/\D/g, '').substring(0, 4);
    });

    // Validar formulario antes de procesar pago
    document.querySelector('.confirm-payment-btn').addEventListener('click', function(e) {
        e.preventDefault();
        
        const cardFields = Object.values(cardInputs);
        const isCardValid = cardFields.every(input => input?.value.trim() !== '');

        if (!isCardValid) {
            alert('Por favor complete todos los campos de la tarjeta');
            return;
        }

        // Simular procesamiento de pago
        Swal.fire({
            title: 'Procesando pago...',
            text: 'Por favor espere',
            allowOutsideClick: false,
            showConfirmButton: false,
            willOpen: () => {
                Swal.showLoading();
            }
        });

        // Simular delay de procesamiento
        setTimeout(() => {
            Swal.fire({
                title: '¡Pago exitoso!',
                text: 'Su compra ha sido procesada correctamente',
                icon: 'success',
                confirmButtonText: 'Descargar Boleta',
                showCancelButton: true,
                cancelButtonText: 'Cerrar',
                confirmButtonColor: '#0A5EFC'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Aquí se implementará la generación de la boleta con Apache POI
                    alert('La función de descarga de boleta será implementada próximamente');
                }
            });

            // Limpiar carrito después del pago exitoso
            localStorage.setItem('cart', '[]');
            loadCartItems();
        }, 2000);
    });

    // Cargar los items del carrito al iniciar
    loadCartItems();
}); 