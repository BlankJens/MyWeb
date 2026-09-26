import React, { useState, useEffect } from 'react';
import { getProducts, deleteProduct, createProduct } from './api';
import './App.css';

function App() {
  const [products, setProducts] = useState([]);
  const [view, setView] = useState('list');
  const [formData, setFormData] = useState({ prodName: '', price: '', image: null });

  const loadProducts = async () => {
    try {
      const data = await getProducts();
      setProducts(data);
    } catch (error) {
      console.error("Erro ao buscar produtos:", error);
    }
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
    } catch (error) {
      console.error("Erro ao remover produto:", error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'image') {
      setFormData({ ...formData, image: files[0] }); 
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = new FormData();
      data.append('prodName', formData.prodName);
      data.append('price', Number(formData.price));
      
      if (formData.image) {
        data.append('image', formData.image);
      }

      await createProduct(data);
      setFormData({ prodName: '', price: '', image: null });
      setView('list');
    } catch (error) {
      console.error("Erro ao salvar produto:", error);
    }
  };

  if (view === 'add') {
    return (
      <div className="container">
        <h1 className="title">Adicionar Produto</h1>
        <form className="form-container" onSubmit={handleSubmit}>
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
          <input
            type="file"
            name="image"
            accept="image/*"
            onChange={handleInputChange}
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
        {products && products.map((product) => (
          <li key={product.prodId} className="product-card">
            <img 
              src={product.imageUrl} 
              alt={product.prodName} 
              style={{ width: '150px', height: '150px', objectFit: 'cover', borderRadius: '8px', marginBottom: '10px' }} 
            />
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
