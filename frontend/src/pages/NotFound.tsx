import React from 'react';
import { Typography, Box, Button } from '@mui/material';
import { Link } from 'react-router-dom';

const NotFound: React.FC = () => {
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '50vh',
      }}
    >
      <Typography variant="h1" component="h1" gutterBottom>
        404
      </Typography>
      <Typography variant="h5" component="h2" gutterBottom>
        Page Not Found
      </Typography>
      <Typography variant="body1" sx={{ mb: 4 }}>
        The page you're looking for doesn't exist or has been moved.
      </Typography>
      <Button component={Link} to="/" variant="contained" color="primary">
        Go to Home
      </Button>
    </Box>
  );
};

export default NotFound; 