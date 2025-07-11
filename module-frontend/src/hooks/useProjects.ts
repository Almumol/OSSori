import { useEffect } from 'react';
import { useApi } from './useApi';
import { projectService } from '../services/projectService';

export function useProjects(sort: string) {
  const { data, loading, error, execute } = useApi(projectService.getProjects);

  useEffect(() => {
    execute(sort);
  }, [sort, execute]);

  return {
    projects: data,
    loading,
    error,
    refetch: () => execute(sort),
  };
} 