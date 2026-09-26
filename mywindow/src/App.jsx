import React, { useState, useEffect } from 'react';
import { getProducts, deleteProduct, createProduct } from './api';
import './App.css';

function App() {
  const [products, setProducts] = useState([]);
  const [view, setView] = useState('list');
  const [formData, setFormData] = useState({ prodId: '', prodName: '', price: '' });

  const loadProducts = async () => {
    try {
      const data = await getProducts();
      setProducts(data);
    } catch (error) {}
  };

  useEffect(() => {
    if (view === 'list') {
      loadProducts();
    }
  }, [view]);

  const formatPrice = (price) => {
    return Number(price).toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    });
  };

  const handleRemove = async (id) => {
    try {
      await deleteProduct(id);
      loadProducts();
    } catch (error) {}
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createProduct({
        prodId: Number(formData.prodId),
        prodName: formData.prodName,
        price: Number(formData.price),
      });
      setFormData({ prodId: '', prodName: '', price: '' });
      setView('list');
    } catch (error) {}
  };

  if (view === 'add') {
    return (
      <div className="container">
        <h1 className="title">Adicionar Produto</h1>
        <form className="form-container" onSubmit={handleSubmit}>
          <input
            type="number"
            name="prodId"
            placeholder="ID do Produto"
            value={formData.prodId}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="prodName"
            placeholder="Nome do Produto"
            value={formData.prodName}
            onChange={handleInputChange}
            required
          />
          <input
            type="number"
            name="price"
            placeholder="Preço (Apenas números)"
            value={formData.price}
            onChange={handleInputChange}
            required
          />
          <div className="form-buttons">
            <button type="submit" className="btn btn-success">Salvar</button>
            <button type="button" className="btn btn-secondary" onClick={() => setView('list')}>Voltar</button>
          </div>
        </form>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="header">
        <h1 className="title">Catálogo de Produtos</h1>
        <button className="btn btn-primary" onClick={() => setView('add')}>
          + Adicionar Produto
        </button>
      </div>
      <ul className="product-list">
        {products.map((product) => (
          <li key={product.prodId} className="product-card">
            <span className="product-name">{product.prodName}</span>
            <span className="product-price">{formatPrice(product.price)}</span>
            <button 
              className="btn btn-danger" 
              onClick={() => handleRemove(product.prodId)}
            >
              Remover
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;