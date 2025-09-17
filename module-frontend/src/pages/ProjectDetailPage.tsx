import React from 'react';
import { useParams, Link as RouterLink } from 'react-router-dom';
import {
  Container,
  Typography,
  Box,
  Chip,
  Stack,
  Button,
  Skeleton,
  Paper,
  Divider,
  Link,
  ThemeProvider,
  createTheme
} from '@mui/material';
import { styled } from '@mui/material/styles';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useProjectDetail } from '../hooks/useProjectDetail';
import MarkdownViewer from '../components/MarkdownViewer';

// Create custom theme to match the design system
const theme = createTheme({
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: '8px',
          padding: '8px 16px',
          fontWeight: 500,
        },
      },
    },
    MuiLink: {
      styleOverrides: {
        root: {
          textDecoration: 'none',
          display: 'flex',
          alignItems: 'center',
          gap: '0.5rem',
          '&:hover': {
            textDecoration: 'none',
          },
        },
      },
    },
  },
  typography: {
    fontFamily: "'Noto Sans KR', sans-serif",
    h4: {
      fontWeight: 700,
      fontSize: '2rem',
    },
    h6: {
      fontWeight: 500,
      fontSize: '1.25rem',
    },
    body1: {
      fontSize: '1rem',
      lineHeight: 1.7,
    },
    subtitle2: {
      fontSize: '0.875rem',
      fontWeight: 500,
    }
  },
  palette: {
    primary: {
      main: '#4F46E5',
    },
    secondary: {
      main: '#10B981',
    },
    background: {
      default: '#f9fafb',
      paper: '#ffffff',
    },
    text: {
      primary: '#111827',
      secondary: '#4B5563',
    },
  },
  shape: {
    borderRadius: 8,
  },
});

// Styled components
const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  borderRadius: theme.shape.borderRadius,
  boxShadow: '0 4px 20px rgba(0, 0, 0, 0.05)',
  backgroundColor: '#ffffff',
}));

const StyledChip = styled(Chip)(({ theme }) => ({
  borderRadius: '8px',
  backgroundColor: '#F3F4F6',
  color: theme.palette.text.secondary,
  '&:hover': {
    backgroundColor: '#E5E7EB',
  },
}));

const DetailSkeleton: React.FC = () => (
  <>
    <Box sx={{ mb: 4 }}>
      <Skeleton variant="text" width={200} height={40} sx={{ borderRadius: 1 }} />
    </Box>
    <StyledPaper>
      <Box sx={{ mb: 4 }}>
        <Skeleton variant="text" width="40%" height={40} sx={{ borderRadius: 1 }} />
        <Skeleton variant="text" width="70%" height={24} sx={{ mt: 1, borderRadius: 1 }} />
        <Skeleton variant="text" width="60%" height={24} sx={{ borderRadius: 1 }} />
        <Stack direction="row" spacing={1} sx={{ mt: 2 }}>
          <Skeleton variant="rounded" width={100} height={32} sx={{ borderRadius: 1 }} />
          <Skeleton variant="rounded" width={80} height={32} sx={{ borderRadius: 1 }} />
          <Skeleton variant="rounded" width={120} height={32} sx={{ borderRadius: 1 }} />
        </Stack>
      </Box>

      <Box sx={{ mb: 4 }}>
        <Skeleton variant="text" width="30%" height={32} sx={{ borderRadius: 1 }} />
        <Stack direction="row" spacing={4} sx={{ mt: 2 }}>
          {[...Array(3)].map((_, i) => (
            <Box key={i}>
              <Skeleton variant="text" width={60} height={20} sx={{ borderRadius: 1 }} />
              <Skeleton variant="text" width={40} height={32} sx={{ borderRadius: 1 }} />
            </Box>
          ))}
        </Stack>
      </Box>

      <Divider sx={{ my: 4 }} />

      <Box>
        <Skeleton variant="text" width="20%" height={32} sx={{ borderRadius: 1 }} />
        <Box sx={{ mt: 2 }}>
          <Skeleton variant="text" width={200} height={24} sx={{ borderRadius: 1 }} />
          <Skeleton variant="rectangular" width={180} height={36} sx={{ mt: 2, borderRadius: 1 }} />
        </Box>
      </Box>
    </StyledPaper>
  </>
);

// Helper for each stat item
const StatItem = ({
  icon,
  label,
  value,
  color,
}: {
  icon: string;
  label: string;
  value: React.ReactNode;
  color?: string;
}) => (
  <Box
    sx={{
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      width: '100%',
      minWidth: 120,
      mb: 1,
    }}
  >
    <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
      <i className={icon} style={{ fontSize: 20, color: color || '#888', marginRight: 8 }} />
      <Typography variant="subtitle2" color="text.secondary">
        {label}
      </Typography>
    </Box>
    <Typography variant="h6" sx={{ fontWeight: 700, ml: 1 }}>
      {value}
    </Typography>
  </Box>
);

const ProjectDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { project, loading, error } = useProjectDetail(id ? parseInt(id, 10) : 0);

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 4 }}>
        <Container maxWidth="lg">
          <Button
            component={RouterLink}
            to="/"
            startIcon={<ArrowBackIcon />}
            sx={{ mb: 4 }}
            variant="outlined"
          >
            Back to Projects
          </Button>

          {error ? (
            <Typography color="error" variant="h6">
              Error loading project: {error.message}
            </Typography>
          ) : loading && !project ? (
            <DetailSkeleton />
          ) : project ? (
            <>
              <StyledPaper>
                <Box sx={{ mb: 4 }}>
                  <Typography variant="h4" component="h1" gutterBottom>
                    {project.name}
                  </Typography>
                  <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
                    {project.description}
                  </Typography>
                  <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
                    {(project.tag ?? []).map((tag, index) => (
                      <StyledChip
                        key={index}
                        label={`${tag.name} (${tag.count})`}
                      />
                    ))}
                  </Stack>
                </Box>
                <Stack
                  direction={{ xs: 'column', md: 'row' }}
                  spacing={4}
                  sx={{ mb: 4 }}
                >
                  <Box sx={{ flex: 1, minWidth: 0 }}>
                    <Box sx={{ mb: 4 }}>
                      <Typography variant="h6" gutterBottom>
                        기여 가이드
                      </Typography>
                      <MarkdownViewer url={project.contributionGuide} />
                    </Box>
                    <Box sx={{ mb: 4 }}>
                      <Typography variant="h6" gutterBottom>
                        Links
                      </Typography>
                      <Stack spacing={3} direction="row" justifyContent="space-between">
                        <Button
                          component={RouterLink}
                          to={project.githubLink}
                          target="_blank"
                          rel="noopener noreferrer"
                          startIcon={<i className="ri-github-fill" />}
                          sx={{ mb: 4 }}
                          variant="outlined"
                        >
                          Github Repository
                        </Button>
                        <Button
                          component={RouterLink}
                          to={project.githubLink}
                          target="_blank"
                          rel="noopener noreferrer"
                          startIcon={<i className="ri-global-line" />}
                          sx={{ mb: 4 }}
                          variant="outlined"
                        >
                          공식 웹사이트
                        </Button>
                        <Button
                          component={RouterLink}
                          to={project.githubLink}
                          target="_blank"
                          rel="noopener noreferrer"
                          startIcon={<i className="ri-book-2-line" />}
                          sx={{ mb: 4 }}
                          variant="outlined"
                        >
                          문서
                        </Button>
                        {project.contributionGuide && (
                          <Button
                            variant="contained"
                            color="primary"
                            href={project.contributionGuide}
                            target="_blank"
                            rel="noopener noreferrer"
                          >
                            View Contribution Guide
                          </Button>
                        )}
                      </Stack>
                    </Box>
                  </Box>
                  
                  <Box sx={{ flex: 1, minWidth: 0 }}>
                    <Box sx={{ mb: 4 }}>
                      <Stack
                        direction={{ xs: 'column' }}
                        spacing={1}
                        sx={{
                          bgcolor: '#F9FAFB',
                          borderRadius: 2,
                          p: 3,
                          flexWrap: 'wrap',
                          justifyContent: 'space-between',
                        }}
                      >
                        <Typography variant="h6" gutterBottom sx={{ fontWeight: 700 }}>
                          프로젝트 통계
                        </Typography>
                        <StatItem icon="ri-star-fill" label="스타" value={project.countingStar?.toLocaleString()} color="#FCD34D" />
                        <StatItem icon="ri-error-warning-fill" label="이슈" value={project.issueCount?.toLocaleString()} color="#F87171" />
                        <StatItem icon="ri-bar-chart-2-fill" label="이슈 빈도" value={project.issueFrequency} color="#6366F1" />
                        <StatItem icon="ri-time-fill" label="평균 PR 반영" value={project.timeToMerge + '일'} color="#F59E42" />
                        <StatItem icon="ri-bar-chart-fill" label="PR 빈도" value={project.pullRequestFrequency} color="#6366F1" />
                        <StatItem icon="ri-user-add-fill" label="기여자 수" value={project.uniqueContributors} color="#10B981" />
                        <StatItem icon="ri-arrow-up-s-fill" label="스타 증감" value={project.starDifference} color="#F59E42" />
                        <StatItem icon="ri-timer-flash-fill" label="첫 PR 응답(일)" value={project.firstResponseTimeOfPullRequest} color="#A78BFA" />
                      </Stack>
                    </Box>
                  </Box>
                </Stack>
                <Divider sx={{ my: 4 }} />
              </StyledPaper>
            </>
          ) : null}
        </Container>
      </Box>
    </ThemeProvider>
  );
};

export default ProjectDetailPage; 