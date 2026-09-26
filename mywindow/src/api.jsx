export const getProducts = async () => {
  const response = await fetch('/api/products');
  return response.json();
};

export const createProduct = async (formDataPayload) => {
  await fetch('/api/products', {
    method: 'POST',
    body: formDataPayload, 
  });
};


export const deleteProduct = async (productId) => {
  await fetch(`/api/products/${productId}`, {
    method: 'DELETE',
  });
};
