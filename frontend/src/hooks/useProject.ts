import { useEffect } from 'react';
import { useApi } from './useApi';
import { projectService } from '../services/projectService';
import { ProjectDetail } from '../types';

export function useProject(projectId: number) {
  const {
    data: project,
    loading,
    error,
    execute: fetchProject,
  } = useApi<ProjectDetail>(() => projectService.getProjectById(projectId));

  useEffect(() => {
    fetchProject();
  }, [fetchProject]);

  return {
    project,
    loading,
    error,
    refetch: fetchProject,
  };
} 