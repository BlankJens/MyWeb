export const getProducts = async () => {
  const response = await fetch('http://localhost:8080/api/products');
  return response.json();
};

export const createProduct = async (productData) => {
  await fetch('http://localhost:8080/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(productData),
  });
};

export const deleteProduct = async (productId) => {
  await fetch(`http://localhost:8080/api/products/${productId}`, {
    method: 'DELETE',
  });
};