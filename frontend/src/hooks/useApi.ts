import { useState, useCallback } from 'react';
import { ApiError } from '../types';

interface UseApiResponse<T> {
  data: T | null;
  loading: boolean;
  error: ApiError | null;
  execute: (...args: any[]) => Promise<void>;
}

export function useApi<T>(
  apiFunc: (...args: any[]) => Promise<T>
): UseApiResponse<T> {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<ApiError | null>(null);

  const execute = useCallback(
    async (...args: any[]) => {
      try {
        setLoading(true);
        setError(null);
        const result = await apiFunc(...args);
        setData(result);
      } catch (err: any) {
        setError({
          message: err.response?.data?.message || err.message || 'An error occurred',
          status: err.response?.status || 500,
          timestamp: new Date().toISOString(),
        });
      } finally {
        setLoading(false);
      }
    },
    [apiFunc]
  );

  return { data, loading, error, execute };
} 