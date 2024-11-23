function initializeData() {
    if (!localStorage.getItem('productData')) {
        const productData = [
            {
                nombre: "Nombre del producto",
                talla: "M",
                despacho: "Entrega a domicilio",
                cantidad: 1,
                tienda: "Nombre de la tienda",
                imagen: "url_de_la_imagen",
                precio: 19.99
            }
        ];
        localStorage.setItem('productData', JSON.stringify(productData));
    }
}

document.addEventListener('DOMContentLoaded', () => {
    initializeData();
    loadProductsFromStorage();
    updateOrderCount();
});

function loadProductsFromStorage() {
    const productData = JSON.parse(localStorage.getItem('productData')) || [];
    const tableBody = document.getElementById('productTableBody');
    tableBody.innerHTML = '';

    productData.forEach((product, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="product-info">
                <div class="product-container">
                    <img src="${product.imagen}" alt="${product.nombre}" style="width: 50px; height: 50px; object-fit: cover;">
                    <div class="product-text">
                        <div class="product-name">${product.nombre}</div>
                        <div class="store-name">${product.tienda}</div>
                    </div>
                </div>
            </td>
            <td class="product-size">${product.talla}</td>
            <td class="product-delivery">${product.despacho}</td>
            <td>$${product.precio}</td>
            <td>
                <div class="quantity-controls">
                    <button class="quantity-btn minus" onclick="updateQuantity(${index}, -1)">
                        <i class="fas fa-minus"></i>
                    </button>
                    <input type="number" min="1" value="${product.cantidad}" 
                           class="quantity-input" onchange="updateQuantityInput(${index}, this.value)">
                    <button class="quantity-btn plus" onclick="updateQuantity(${index}, 1)">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
            </td>
            <td>$${(product.precio * product.cantidad).toFixed(2)}</td>
            <td>
                <button class="delete-btn" onclick="deleteProduct(${index})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    updateTotals();
}

function updateQuantity(index, change) {
    const productData = JSON.parse(localStorage.getItem('productData')) || [];
    const newQuantity = Math.max(1, productData[index].cantidad + change);
    productData[index].cantidad = newQuantity;
    localStorage.setItem('productData', JSON.stringify(productData));
    loadProductsFromStorage();
}

function updateQuantityInput(index, value) {
    const productData = JSON.parse(localStorage.getItem('productData')) || [];
    productData[index].cantidad = Math.max(1, parseInt(value) || 1);
    localStorage.setItem('productData', JSON.stringify(productData));
    loadProductsFromStorage();
}

function deleteProduct(index) {
    const productData = JSON.parse(localStorage.getItem('productData')) || [];
    productData.splice(index, 1);
    localStorage.setItem('productData', JSON.stringify(productData));
    loadProductsFromStorage();
    updateOrderCount();
}

function updateTotals() {
    const productData = JSON.parse(localStorage.getItem('productData')) || [];
    const subtotal = productData.reduce((sum, product) => sum + (product.precio * product.cantidad), 0);
    const discount = 0; // Puedes implementar tu lógica de descuento aquí
    const total = subtotal - discount;

    document.querySelector('.summary-content').innerHTML = `
        <div class="summary-item">
            <span>Subtotal:</span>
            <span>$${subtotal.toFixed(2)}</span>
        </div>
        <div class="summary-item">
            <span>Descuento:</span>
            <span class="discount">-$${discount.toFixed(2)}</span>
        </div>
        <div class="summary-item total">
            <span>Total:</span>
            <span>$${total.toFixed(2)}</span>
        </div>
        <button class="buy-btn">Proceder al Pago</button>
    `;
}

function updateOrderCount() {
    const productData = JSON.parse(localStorage.getItem('productData')) || [];
    const totalItems = productData.reduce((sum, product) => sum + product.cantidad, 0);
    document.querySelector('.order-count').textContent = `${totalItems} productos agregados`;
} 