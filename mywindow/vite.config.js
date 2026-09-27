import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    // A porta 8080 pertence ao backend Spring (spring.server.port).
    // O dev server do Vite nao pode usa-la, senao um dos dois falha ao subir.
    port: 5173,
    proxy: {
      // Sem proxy, /api/products cai no fallback SPA do Vite e devolve o
      // index.html com HTTP 200, o response.json() estoura e a lista fica vazia.
      '/api': 'http://localhost:8080',
    },
  },
})
