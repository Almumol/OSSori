import React, { useState } from 'react';
import { useParams, Link as RouterLink } from 'react-router-dom';
import { 
  Container, 
  Card, 
  CardContent, 
  Typography, 
  Box,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Chip,
  Stack,
  Skeleton,
  ThemeProvider,
  createTheme,
  CardProps
} from '@mui/material';
import { Unstable_Grid2 as Grid } from '@mui/material';
import { ProjectSortOption, ProjectSummary } from '../types';
import { useProjects } from '../hooks/useProjects';
import { styled } from '@mui/material/styles';

// Create custom theme to match the design system
const theme = createTheme({
  components: {
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: '8px',
          boxShadow: '0 4px 20px rgba(0, 0, 0, 0.05)',
          transition: 'box-shadow 0.3s ease-in-out',
          '&:hover': {
            boxShadow: '0 8px 30px rgba(0, 0, 0, 0.12)',
          },
        },
      },
    },
    MuiSelect: {
      styleOverrides: {
        select: {
          borderRadius: '8px',
        },
      },
    },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          borderRadius: '8px',
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
    h5: {
      fontWeight: 600,
      fontSize: '1.25rem',
    },
    h6: {
      fontWeight: 500,
      fontSize: '1.1rem',
    },
    body1: {
      fontSize: '1rem',
      lineHeight: 1.7,
    },
    body2: {
      fontSize: '0.875rem',
      lineHeight: 1.6,
    },
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

const StyledChip = styled(Chip)(({ theme }) => ({
  borderRadius: '8px',
  backgroundColor: '#F3F4F6',
  color: theme.palette.text.secondary,
  '&:hover': {
    backgroundColor: '#E5E7EB',
  },
}));

const StyledCard = styled(Card, {
  shouldForwardProp: (prop) => 
    prop !== 'component' && prop !== 'to'
})<CardProps>({
  height: '100%',
  display: 'flex',
  flexDirection: 'column',
  textDecoration: 'none',
  backgroundColor: '#ffffff',
});

const ProjectSkeleton: React.FC = () => (
  <Grid xs={12} sm={6} md={4}>
    <StyledCard>
      <CardContent>
        <Skeleton variant="text" width="60%" height={32} sx={{ mb: 1, borderRadius: 1 }} />
        <Skeleton variant="text" width="100%" height={20} sx={{ borderRadius: 1 }} />
        <Skeleton variant="text" width="90%" height={20} sx={{ borderRadius: 1 }} />
        <Box sx={{ mt: 2, mb: 2 }}>
          <Stack direction="row" spacing={1}>
            <Skeleton variant="rounded" width={80} height={24} sx={{ borderRadius: 1 }} />
            <Skeleton variant="rounded" width={60} height={24} sx={{ borderRadius: 1 }} />
            <Skeleton variant="rounded" width={70} height={24} sx={{ borderRadius: 1 }} />
          </Stack>
        </Box>
        <Box sx={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          mt: 'auto',
          pt: 2,
          borderTop: '1px solid',
          borderColor: 'divider'
        }}>
          <Skeleton variant="text" width={60} sx={{ borderRadius: 1 }} />
          <Skeleton variant="text" width={60} sx={{ borderRadius: 1 }} />
          <Skeleton variant="text" width={60} sx={{ borderRadius: 1 }} />
        </Box>
      </CardContent>
    </StyledCard>
  </Grid>
);

interface ProjectCardProps extends Omit<CardProps, 'children'> {
  project: ProjectSummary;
}

const ProjectCard: React.FC<ProjectCardProps> = ({ project, ...props }) => (
  <Grid xs={12} sm={6} md={4}>
    <RouterLink
      to={`/project/${project.id}`}
      style={{ 
        textDecoration: 'none',
        display: 'flex',
        height: '100%'
      }}
    >
      <StyledCard sx={{ width: '100%' }}>
        <CardContent sx={{ flexGrow: 1 }}>
          <Typography gutterBottom variant="h5" component="h2">
            {project.name}
          </Typography>
          <Typography 
            variant="body2" 
            color="text.secondary" 
            sx={{ 
              mb: 2,
              display: '-webkit-box',
              WebkitLineClamp: 2,
              WebkitBoxOrient: 'vertical',
              overflow: 'hidden',
            }}
          >
            {project.description}
          </Typography>
          <Stack 
            direction="row" 
            spacing={1} 
            sx={{ 
              mb: 2, 
              flexWrap: 'wrap', 
              gap: 1 
            }}
          >
            {(project.tag ?? []).map((tag: ProjectSummary['tag'][0], index: number) => (
              <StyledChip
                key={index}
                label={`${tag.name} (${tag.count})`}
                size="small"
              />
            ))}
          </Stack>
          <Box sx={{ 
            display: 'flex', 
            justifyContent: 'space-between', 
            mt: 'auto',
            pt: 2,
            borderTop: '1px solid',
            borderColor: 'divider'
          }}>
            <Typography variant="body2" sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
              <i className="ri-star-fill" style={{ color: '#FCD34D' }} />
              {project.countingStar.toLocaleString()}
            </Typography>
            <Typography variant="body2" sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
              <i className="ri-error-warning-fill" style={{ color: '#F87171' }} />
              {project.issueCount.toLocaleString()}
            </Typography>
            <Typography variant="body2" sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
              <i className="ri-pulse-fill" style={{ color: '#10B981' }} />
              {(project.activityRate * 100).toFixed(0)}%
            </Typography>
          </Box>
        </CardContent>
      </StyledCard>
    </RouterLink>
  </Grid>
);

const ProjectsPage: React.FC = () => {
  const [sortBy, setSortBy] = useState<ProjectSortOption>('countingStar');
  const { projects, loading, error } = useProjects(sortBy);

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 4 }}>
        <Container maxWidth="lg">
          <Box sx={{ mb: 4 }}>
            <FormControl sx={{ minWidth: 200 }}>
              <InputLabel id="sort-select-label">Sort by</InputLabel>
              <Select
                labelId="sort-select-label"
                value={sortBy}
                label="Sort by"
                onChange={(e) => setSortBy(e.target.value as ProjectSortOption)}
                disabled={loading && projects.length === 0}
              >
                <MenuItem value="countingStar">Stars</MenuItem>
                <MenuItem value="issueCount">Issues</MenuItem>
                <MenuItem value="activityRate">Activity Rate</MenuItem>
              </Select>
            </FormControl>
          </Box>

          {error ? (
            <Typography color="error" variant="h6">
              Error loading projects: {error.message}
            </Typography>
          ) : (
            <Box sx={{ flexGrow: 1 }}>
              <Grid container spacing={3}>
                {loading && projects.length === 0 ? (
                  <>
                    {[...Array(6)].map((_, index) => (
                      <ProjectSkeleton key={index} />
                    ))}
                  </>
                ) : (
                  projects.map((project) => (
                    <ProjectCard key={project.id} project={project} />
                  ))
                )}
              </Grid>
            </Box>
          )}
        </Container>
      </Box>
    </ThemeProvider>
  );
};

export default ProjectsPage; 