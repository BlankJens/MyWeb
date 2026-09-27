// O fetch() so rejeita em erro de rede; em 4xx/5xx ele resolve normal.
// Sem checar response.ok, um upload que falhou com 500 era tratado como sucesso.
const checkResponse = async (response) => {
  if (!response.ok) {
    throw new Error(await response.text());
  }
  return response;
};

export const getProducts = async () => {
  const response = await fetch('/api/products');
  return (await checkResponse(response)).json();
};

export const createProduct = async (formDataPayload) => {
  const response = await fetch('/api/products', {
    method: 'POST',
    body: formDataPayload,
  });
  // O controller devolve ResponseEntity<String>, que sai como text/plain.
  // Por isso .text() e nao .json() aqui.
  return (await checkResponse(response)).text();
};

export const deleteProduct = async (productId) => {
  const response = await fetch(`/api/products/${productId}`, {
    method: 'DELETE',
  });
  return checkResponse(response);
};
