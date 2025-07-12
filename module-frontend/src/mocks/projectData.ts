import { ProjectSummary, ProjectDetail } from '../types';

export const mockProjects: ProjectSummary[] = [
  {
    id: 1,
    name: "React",
    description: "A JavaScript library for building user interfaces with a declarative and efficient approach to rendering.",
    tag: [
      { name: "javascript", count: 5 },
      { name: "frontend", count: 4 },
      { name: "ui", count: 3 }
    ],
    countingStar: 203400,
    issueCount: 1248,
    activityRate: 0.98,
    issueFrequency: 1.2,
    timeToMerge: 2.5,
    pullRequestFrequency: 0.8,
    uniqueContributors: 10,
    starDifference: 5,
    firstResponseTimeOfPullRequest: 1.1
  },
  {
    id: 2,
    name: "TensorFlow",
    description: "An open-source platform for machine learning and deep learning, supporting research and production workflows.",
    tag: [
      { name: "python", count: 5 },
      { name: "machine-learning", count: 4 },
      { name: "ai", count: 3 }
    ],
    countingStar: 178300,
    issueCount: 987,
    activityRate: 0.92,
    issueFrequency: 1.0,
    timeToMerge: 3.0,
    pullRequestFrequency: 0.9,
    uniqueContributors: 8,
    starDifference: 3,
    firstResponseTimeOfPullRequest: 1.5
  },
  {
    id: 3,
    name: "VS Code",
    description: "A lightweight but powerful source code editor with support for multiple programming languages.",
    tag: [
      { name: "typescript", count: 5 },
      { name: "editor", count: 4 },
      { name: "tools", count: 3 }
    ],
    countingStar: 152900,
    issueCount: 756,
    activityRate: 0.95,
    issueFrequency: 1.1,
    timeToMerge: 2.0,
    pullRequestFrequency: 0.85,
    uniqueContributors: 12,
    starDifference: 4,
    firstResponseTimeOfPullRequest: 1.2
  },
  {
    id: 4,
    name: "Kubernetes",
    description: "An open-source system for automating deployment, scaling, and management of containerized applications.",
    tag: [
      { name: "go", count: 5 },
      { name: "cloud", count: 4 },
      { name: "containers", count: 3 }
    ],
    countingStar: 101200,
    issueCount: 1543,
    activityRate: 0.89,
    issueFrequency: 1.3,
    timeToMerge: 3.5,
    pullRequestFrequency: 0.7,
    uniqueContributors: 7,
    starDifference: 2,
    firstResponseTimeOfPullRequest: 1.8
  },
  {
    id: 5,
    name: "Next.js",
    description: "A React framework for production-grade applications with server-side rendering and static site generation.",
    tag: [
      { name: "javascript", count: 5 },
      { name: "react", count: 4 },
      { name: "framework", count: 3 }
    ],
    countingStar: 98700,
    issueCount: 876,
    activityRate: 0.94,
    issueFrequency: 1.1,
    timeToMerge: 2.2,
    pullRequestFrequency: 0.95,
    uniqueContributors: 15,
    starDifference: 6,
    firstResponseTimeOfPullRequest: 1.0
  }
];

export const mockProjectDetails: Record<number, ProjectDetail> = {
  1: {
    ...mockProjects[0],
    contributionGuide: "react-contribution-guide",
    githubLink: "https://github.com/facebook/react"
  },
  2: {
    ...mockProjects[1],
    contributionGuide: "tensorflow-contribution-guide",
    githubLink: "https://github.com/tensorflow/tensorflow"
  },
  3: {
    ...mockProjects[2],
    contributionGuide: "vscode-contribution-guide",
    githubLink: "https://github.com/microsoft/vscode"
  },
  4: {
    ...mockProjects[3],
    contributionGuide: "kubernetes-contribution-guide",
    githubLink: "https://github.com/kubernetes/kubernetes"
  },
  5: {
    ...mockProjects[4],
    contributionGuide: "nextjs-contribution-guide",
    githubLink: "https://github.com/vercel/next.js"
  }
}; 