import { useState, useEffect, useCallback } from 'react';
import { projectService } from '../services/projectService';
import { ProjectDetail } from '../types';

export function useProjectDetail(id: number) {
  const [project, setProject] = useState<ProjectDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  const fetchProject = useCallback(async () => {
    try {
      // Only show loading if we don't have data yet
      if (!project) {
        setLoading(true);
      }

      const data = await projectService.getProjectById(id);
      setProject(data);
      setError(null);
    } catch (err) {
      setError(err instanceof Error ? err : new Error('Failed to fetch project details'));
      setProject(null);
    } finally {
      setLoading(false);
    }
  }, [id, project]);

  useEffect(() => {
    const fetchProject = async () => {
      // Only show loading if we don't have data yet
      if (!project) {
        setLoading(true);
      }

      const data = await projectService.getProjectById(id);
      setProject(data);
      setError(null);
    };
    fetchProject();
  }, [id]);

  return {
    project,
    loading,
    error,
    refetch: fetchProject
  };
} 