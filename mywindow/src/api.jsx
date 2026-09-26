export const getProducts = async () => {
  const response = await fetch('/api/products');
  return response.json();
};

export const createProduct = async (productData) => {
  await fetch('/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(productData),
  });
};

export const deleteProduct = async (productId) => {
  await fetch(`/api/products/${productId}`, {
    method: 'DELETE',
  });
};
