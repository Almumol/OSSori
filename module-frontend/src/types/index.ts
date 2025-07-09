// Common error response
export interface ApiError {
  message: string;
  status: number;
  timestamp: string;
}

// Project tag type
export interface ProjectTag {
  name: string;
  count: number;
}

// Basic project information
export interface ProjectSummary {
  id: number;
  name: string;
  description: string;
  tag: ProjectTag[];
  countingStar: number;
  issueCount: number;
  activityRate: number;
  issueFrequency: number;
  timeToMerge: number;
  pullRequestFrequency: number;
  uniqueContributors: number;
  starDifference: number;
  firstResponseTimeOfPullRequest: number;
}

// Detailed project information
export interface ProjectDetail extends ProjectSummary {
  contributionGuide: string; // S3 bucket key
  githubLink: string;
}

// Sort options for projects
export type ProjectSortOption = 'countingStar' | 'issueCount' | 'activityRate';

// Projects list response
export interface ProjectsResponse {
  projects: ProjectSummary[];
}

// Add your model interfaces here
// Example:
// export interface User {
//   id: number;
//   username: string;
//   email: string;
// } 