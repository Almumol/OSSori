import React from 'react';
import { Box, AppBar, Toolbar, Typography, Container, Link } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <AppBar position="static" color="transparent" elevation={0}>
        <Toolbar>
          <Link
            component={RouterLink}
            to="/"
            underline="none"
            color="inherit"
            sx={{
              fontWeight: 700,
              fontSize: '1.5rem',
              fontFamily: "'Noto Sans KR', sans-serif",
              letterSpacing: 1,
              '&:hover': { color: 'primary.main' },
              transition: 'color 0.2s'
            }}
          >
            OSSori
          </Link>
        </Toolbar>
      </AppBar>
      <Container component="main" sx={{ flexGrow: 1, py: 4 }}>
        {children}
      </Container>
      <Box
        component="footer"
        sx={{
          py: 3,
          px: 2,
          mt: 'auto',
          backgroundColor: (theme) => theme.palette.grey[200],
        }}
      >
        <Container maxWidth="sm">
          <Typography variant="body2" color="text.secondary" align="center">
            © {new Date().getFullYear()} OSSori. All rights reserved.
          </Typography>
        </Container>
      </Box>
    </Box>
  );
};

export default Layout; 