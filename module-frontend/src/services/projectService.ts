import { ProjectsResponse, ProjectDetail, ProjectSortOption, ProjectSummary } from '../types';
import { mockProjects, mockProjectDetails } from '../mocks/projectData';
import api from './api';

// Cache for projects data
let projectsCache: Record<ProjectSortOption, ProjectSummary[]> = {
  countingStar: [],
  issueCount: [],
  activityRate: []
};

// Helper function to sort projects
const sortProjects = (projects: ProjectSummary[], sort?: ProjectSortOption) => {
  if (!sort) return projects;

  return [...projects].sort((a, b) => {
    switch (sort) {
      case 'countingStar':
        return b.countingStar - a.countingStar;
      case 'issueCount':
        return b.issueCount - a.issueCount;
      case 'activityRate':
        return b.activityRate - a.activityRate;
      default:
        return 0;
    }
  });
};

export const projectService = {  
  // Get all projects with optional sorting
  getProjects: async (sort: ProjectSortOption = 'countingStar'): Promise<ProjectsResponse> => {
    // Return cached data if available
    if (projectsCache[sort].length > 0) {
      return {
        projects: projectsCache[sort]
      };
    }

    // Simulate API delay only for first load
    await new Promise(resolve => setTimeout(resolve, 500));

    const response = await api.get(`/v1/projects/`);
    const sortedProjects = sortProjects(response.data.projects, sort);
    projectsCache[sort] = sortedProjects;

    return {
      projects: sortedProjects
    };
  },

  // Get project details by ID
  getProjectById: async (id: number): Promise<ProjectDetail> => {
    const response = await api.get(`/v1/projects/${id}`);
    return response.data;
  },

  // Clear cache (useful for testing)
  clearCache: () => {
    projectsCache = {
      countingStar: [],
      issueCount: [],
      activityRate: []
    };
  }
}; 