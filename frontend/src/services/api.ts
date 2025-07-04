import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Response interceptor - for basic error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Handle basic error cases
    if (error.response) {
      switch (error.response.status) {
        case 404:
          console.error('Resource not found:', error.response.config.url);
          break;
        default:
          console.error('API Error:', error.response.status, error.response.data);
          break;
      }
    } else if (error.request) {
      console.error('Network Error:', error.message);
    }
    return Promise.reject(error);
  }
);

export default api; 