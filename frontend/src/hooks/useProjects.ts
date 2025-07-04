import { useEffect, useState, useCallback, useRef } from 'react';
import { useApi } from './useApi';
import { projectService } from '../services/projectService';
import { ProjectsResponse, ProjectSortOption, ProjectSummary } from '../types';

export function useProjects(initialSort: ProjectSortOption = 'countingStar') {
  const [projects, setProjects] = useState<ProjectSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const currentSort = useRef(initialSort);

  const fetchProjects = useCallback(async (sort: ProjectSortOption) => {
    try {
      // Only show loading state on first load or when changing sort
      if (projects.length === 0) {
        setLoading(true);
      }
      
      const response = await projectService.getProjects(sort);
      setProjects(response.projects);
      setError(null);
    } catch (err) {
      setError(err instanceof Error ? err : new Error('Failed to fetch projects'));
    } finally {
      setLoading(false);
    }
  }, [projects.length]);

  // Handle sort changes
  const handleSort = useCallback((sort: ProjectSortOption) => {
    if (currentSort.current !== sort) {
      currentSort.current = sort;
      fetchProjects(sort);
    }
  }, [fetchProjects]);

  // Initial fetch
  useEffect(() => {
    fetchProjects(initialSort);
  }, [initialSort, fetchProjects]);

  return {
    projects,
    loading,
    error,
    setSort: handleSort
  };
} 