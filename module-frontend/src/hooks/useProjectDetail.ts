import { useEffect } from 'react';
import { useApi } from './useApi';
import { projectService } from '../services/projectService';

export function useProjectDetail(id: number) {
  const { data, loading, error, execute } = useApi(projectService.getProjectById);

  useEffect(() => {
    if (id) execute(id);
  }, [id, execute]);

  return {
    project: data,
    loading,
    error,
    refetch: () => execute(id),
  };
}