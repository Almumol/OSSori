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
    activityRate: 0.98
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
    activityRate: 0.92
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
    activityRate: 0.95
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
    activityRate: 0.89
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
    activityRate: 0.94
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