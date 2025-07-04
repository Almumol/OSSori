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

                <Box sx={{ mb: 4 }}>
                  <Typography variant="h6" gutterBottom>
                    Project Statistics
                  </Typography>
                  <Stack 
                    direction={{ xs: 'column', sm: 'row' }} 
                    spacing={{ xs: 2, sm: 4 }}
                    sx={{ 
                      '& > div': {
                        p: 3,
                        bgcolor: '#F9FAFB',
                        borderRadius: 1,
                        flex: 1,
                      }
                    }}
                  >
                    <Box>
                      <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                        Stars
                      </Typography>
                      <Typography variant="h6" color="primary">
                        {project.countingStar.toLocaleString()}
                      </Typography>
                    </Box>
                    <Box>
                      <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                        Issues
                      </Typography>
                      <Typography variant="h6" color="primary">
                        {project.issueCount.toLocaleString()}
                      </Typography>
                    </Box>
                    <Box>
                      <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                        Activity Rate
                      </Typography>
                      <Typography variant="h6" color="primary">
                        {(project.activityRate * 100).toFixed(0)}%
                      </Typography>
                    </Box>
                  </Stack>
                </Box>

                <Divider sx={{ my: 4 }} />

                <Box sx={{ mb: 4 }}>
                  <Typography variant="h6" gutterBottom>
                    Links
                  </Typography>
                  <Stack spacing={2}>
                    <Link
                      href={project.githubLink}
                      target="_blank"
                      rel="noopener noreferrer"
                      sx={{ 
                        display: 'flex', 
                        alignItems: 'center', 
                        gap: 1,
                        color: 'primary.main',
                        '&:hover': {
                          color: 'primary.dark',
                        }
                      }}
                    >
                      <i className="ri-github-fill" /> GitHub Repository
                    </Link>
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
              </StyledPaper>
            </>
          ) : null}
        </Container>
      </Box>
    </ThemeProvider>
  );
};

export default ProjectDetailPage; 